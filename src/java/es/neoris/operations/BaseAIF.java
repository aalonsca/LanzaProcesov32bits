package es.neoris.operations;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.namespace.QName;

import com.amdocs.cih.common.core.MaskInfo;
import com.amdocs.cih.common.core.sn.ApplicationContext;
import com.amdocs.cih.common.datatypes.OrderingContext;
import com.amdocs.svcparams.IOmsServicesCreateOMSSessionResults;
import com.amdocs.svcparams.IOmsServicesRetrieveOrderResults;
import com.amdocs.svcparams.IOmsServicesStartOrderResults;
import com.clarify.cbo.Application;
import com.clarify.cbo.Session;
import com.clarify.cbo.UamsHelper;

import es.neoris.operations.loginServices.LoginServicesBindingStub;
import es.neoris.operations.loginServices.LoginServicesLocator;
import es.neoris.operations.oms.createSession.CreateSession;
import es.neoris.operations.oms.launchOrder.LaunchOrder;
import es.neoris.operations.oms.retrieveOrder.RetrieveOrder;

/**
 * Execute an AIF service. Previously, always execute CreateSession AIF.
 * @author NEORIS
 * @version: 1.0
 */
public class BaseAIF {

	// Variables to control Amdocs session
	protected static Application clfyApp = null;
	protected static Session clfySession = null;

	//Session variables variables
	protected static String ticketAMS;
	protected static String profileID;
	
	protected static ApplicationContext appContext = new ApplicationContext();
	protected static OrderingContext orderingContext = new OrderingContext();
	protected static MaskInfo maskInfo = new MaskInfo();
	protected static Object s_PredefinedLoginLatch;

	// Properties from .properties file
	static final String sNombreFich = "baseaif.properties";
	static final String sRutaIni = "res/";
	private static HashMap<String, String> prop = new HashMap<String, String>();
	

	/**
	 * Main function
	 * @param args
	 *  
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		//Previous validation
		if (args.length == 0) {
			throw new Exception("Error. Input parameters missing.");
		}
		
		BaseAIF.startServices(args);
		
	}

	/**
	 * Constructor
	 */
	public BaseAIF () {
		//no-op constructor
		
	}


	/**
	 * Start services
	 */
	public static void startServices (String[] args)
	throws Exception {

		try {
			
			// Initialize remote services
			clfyApp = new Application();
			//System.out.println("Application generated OK");
			clfyApp.initialize();
			System.out.println("Application initialize OK");
			
			//String strModuleDir = clfyApp.getModuleDir();
			//System.out.println("Ruta clarify.env: " + strModuleDir);
			
			if (Boolean.getBoolean("AsmLogin")) 
				System.out.println("Asm login enabled");			
			
			clfySession = clfyApp.getGlobalSession();
			System.out.println("global session ok");
		
			clfySession.establishOnCurrentThread();
			System.out.println("Session established OK");
			
			BaseAIF.ticketAMS = createToken();
			
			if ("".equals(BaseAIF.ticketAMS)) {
				System.out.println("ERROR generating ASM ticket ");
				System.exit(0);
			}

			UamsHelper.setTicketOnSession(clfySession, ticketAMS, null);
			System.out.println("Setting ticket " + ticketAMS + " ok");

			// Initialize static properties
			BaseAIF.setInputAppContext();
			BaseAIF.setInputOrderingContext();
			BaseAIF.setInputMaskInfo();
			
			
		}catch(Exception e) {
			System.out.println("ERROR " + e.toString());
			System.exit(0);
		}
		
		//New session through .properties values
		CreateSession session = new CreateSession(true);		
		try {

			IOmsServicesCreateOMSSessionResults m_output = new IOmsServicesCreateOMSSessionResults();
			m_output = session.execProc();
			//System.out.println("session.execProc ok " + m_output);

			/*
			profileID = session.execProc().getCreateOMSSessionResponse().getSecurityProfileID(); 
			if (profileID == null) {
				System.out.println("Error getting ticket AMS.");
				throw new Exception("Error getting ticket AMS.");
			}else{
			    BaseAIF.orderingContext.setSecurityToken(profileID);  
			}
			*/
 
		}catch(Exception e) {
			System.out.println("Error creating OMSSession.");
			throw new Exception("Error creating OMSSession.");
		}
		
		
		try {
			 
			// The args[0] contains the service name to execute
			if (! "".equals(args[0])) {
				
				String service =args[0];				
				if ("LAUNCHORDER".equalsIgnoreCase(service)) {
					
					
					//Retrieve the order info
					RetrieveOrder order = new RetrieveOrder(true);
					order.setM_orderId(args[1]); //Fill the orderID through the input param
					
					//Call the service to retrieve the Order object
					IOmsServicesRetrieveOrderResults outOrder = order.execProc(); 
					
					String orderID = outOrder.getRetrieveOrderOutput().getOrder(0).getOrderID().getOrderID();					
					if (orderID == null) {
						System.out.println("Error getting order info" + orderID);
						throw new Exception("Error getting order info: " + orderID);
					}
					
					//LaunchOrder service					
					LaunchOrder process = new LaunchOrder(true);
					if (outOrder.getRetrieveOrderOutput().getOrder(0) != null) {
						process.setM_order(outOrder.getRetrieveOrderOutput().getOrder(0));
						process.setStrIDContract(args[1]);
						process.setStrProcessName(args[2]);
						process.setStrVersion(args[3]);

						
					}else{
						System.out.println("Error retrieving order info");
						throw new Exception("Error retrieving order info");
					}
					
					if (process.getM_order() == null) {
						System.out.println("(2) Error getting order info" + orderID);
						throw new Exception("(2) Error getting order info: " + orderID);
					}
					
					IOmsServicesStartOrderResults output = process.execProc();
					
					if (output == null) {
						System.out.println("Error executing process " + args[1] + " --> " + args[2] + "(" + args[3] + ")");
						throw new Exception("Error executing process " + args[1] + " --> " + args[2] + "(" + args[3] + ")");
						
					}else{
						
						if (process.writeResult() < 0) {
							System.out.println("Error writing final file." + args[1] + " --> " + args[2] + "(" + args[3] + ")");
							throw new Exception("Error writing final file " + args[1] + " --> " + args[2] + "(" + args[3] + ")");
						}
						
					}
					
				}
				/*
				// ARcreateDispute service
				else if ("ARCREATEDISPUTE".equalsIgnoreCase(service)) {
					
					ARcreateDispute process = new ARcreateDispute(true);
					// Fill the data obtains by the input parameters
					process.setAccountId((long) Long.valueOf(args[1])); 
					process.setCmAccountNumber((String) args[2]);
					process.setAmount((double) Double.valueOf(args[3]));
					
					DisputePDt output = process.execProc();
					
					if (output == null) {
						System.out.println("Error executing process " + args[1] + " --> " + args[2] + "(" + args[3] + ")");
						throw new Exception("Error executing process " + args[1] + " --> " + args[2] + "(" + args[3] + ")");
					}
					
					
				}*/
			}
			
		}catch(Exception e) {
			System.out.println("Error executing " + args[0] + "." + e.toString());
			throw new Exception("Error executing " + args[0] + ". Exiting...");
		}
		
		
	}

	/**
	 * Generates a unique id to identify the session
	 * @return
	 */
	private static String createToken()
	{
		String ticket = "";
		
		// Get from .properties file the parameters to call the login webservice
		try {
			getWSProp();

			//Defining the WS configuration
			LoginServicesLocator loginServices = new LoginServicesLocator(prop.get("ADDRESS") + "?wsdl", new QName(prop.get("NAMESPACE"), prop.get("WSDD_SERVICE_NAME")));
			loginServices.setLoginServicesEndpointAddress(prop.get("ADDRESS"));
			loginServices.setEndpointAddress(prop.get("LOCAL_PART"), prop.get("ADDRESS") );
			loginServices.setWSDDServiceName(prop.get("LOCAL_PART"));
			
			//Calling the WS
			LoginServicesBindingStub remote = (LoginServicesBindingStub) loginServices.getLoginServices();
			remote.setnameSpace(prop.get("NAMESPACE"));
			remote.setPortName(new QName(prop.get("NAMESPACE"), prop.get("WSDD_SERVICE_NAME")));
			
			ticket = remote.login(prop.get("USER"), prop.get("PASSWORD"), prop.get("APPID"), prop.get("ENV"));

		}catch(Exception e){
			System.out.println("Error executing createToken " + e.toString());
		}
		
		return ticket;
		
	}

	
	
	/**
	 * Get the configuration for call WS Login
	 */
	
	private static void getWSProp() throws IOException, Exception{

		try {
			Properties properties = BaseAIF.getProperties(sRutaIni, sNombreFich);
			
			prop.put("ADDRESS", properties.getProperty("ADDRESS"));
			prop.put("WSDD_SERVICE_NAME", properties.getProperty("WSDD_SERVICE_NAME"));
			prop.put("NAMESPACE", properties.getProperty("NAMESPACE"));
			prop.put("LOCAL_PART", properties.getProperty("LOCAL_PART"));		
			prop.put("USER", properties.getProperty("USER"));
			prop.put("PASSWORD", properties.getProperty("PASSWORD"));
			prop.put("ENV", properties.getProperty("ENV"));
		}catch(Exception e) {
			System.out.println("Error getting " + sNombreFich + ": " + e.getLocalizedMessage());
			throw new Exception("Error reading .properties file");
			
		}
	}
	

	/**
	 * Gets service configuration from .properties file
	 * @throws IOException
	 */
	protected static Properties getProperties(String sRutaIni, String sNombreFich) 
			throws IOException, Exception {
		
		try {
			//Get environment values from .properties
			Properties properties = new Properties();
			
			//Open file
			File fProp = new File(sRutaIni, sNombreFich);
			
			if (!fProp.canRead()) {
				throw new IOException("File does not exists: " + fProp.getAbsolutePath());
			}
			
			// Load .properties into local variables y close file
			FileInputStream fiProp = new FileInputStream(fProp);
			properties.load(fiProp);
			fiProp.close();

			return properties;

		}catch(Exception e) {
			System.out.println("ERROR Opening .properties FAILED: " + e.toString()); 
			throw new Exception("Error loading .properties file. Exiting...");
		}
	}		
	

	/**
	 * Prepared WL connection to execute the operation
	 * 
	 * @return 0 -> OK
	 *        -1 -> Error connecting
	 */
	protected static EJBHome createEJBObject(Map<String, String> connectionProp, String JNDI, Boolean debug) {
		
		if (debug) {
			System.out.println("Entering prepareConnWL..."); 
		}
		
		Object objref = null;
		
		// Generating WL connection
		try {
			
			String initialContextFactory = "weblogic.jndi.WLInitialContextFactory";

			// Defining properties to connect 
			Properties propertiesCon = new Properties();
			propertiesCon.put(InitialContext.INITIAL_CONTEXT_FACTORY, initialContextFactory);
			propertiesCon.put(InitialContext.PROVIDER_URL, connectionProp.get("WLS_URL"));
			if (!"".equals(connectionProp.get("WLS_USER")) && connectionProp.get("WLS_USER") != null) {
				propertiesCon.put(InitialContext.SECURITY_PRINCIPAL, connectionProp.get("WLS_USER"));
				propertiesCon.put(InitialContext.SECURITY_CREDENTIALS, connectionProp.get("WLS_PASS") == null ? "" : connectionProp.get("WLS_PASS"));
			}

			//if (debug) {
			//	System.out.println("Properties created: " + propertiesCon.toString());
			//}
			
			// Open a RMI connection to server
			InitialContext context = new InitialContext(propertiesCon);
			objref = context.lookup(JNDI);

			EJBHome AIFservice = (EJBHome) PortableRemoteObject.narrow(objref, EJBHome.class);

			return AIFservice;
			
			
		}catch(Exception e){
			
			if (debug) {
				System.out.println("ERROR APM Session initialization FAILED: " + e.toString());
			}
			
			return null;
		}

		
	}

		

	/**
	 * Initialize ApplicationContext object
	 * @return ApplicationContext
	 */
	private static void setInputAppContext() {
		try {
			Locale locale = BaseAIF.clfySession.getLocale();
			
			if (locale != null)
				BaseAIF.appContext.setFormatLocale(locale);
			

		}catch(Exception e) {
			System.out.print("Error setting App Context");
		}
		
	}
	
	/**
	 * Initialize OrderingContext
	 * @return ApplicationContext
	 */
	private static void setInputOrderingContext() {

		BaseAIF.orderingContext.setSecurityToken(BaseAIF.ticketAMS); 
		
		try {
			Locale locale = BaseAIF.clfySession.getLocale();

			if (locale != null)
				BaseAIF.orderingContext.setLocale(locale);
			
		}catch(Exception e) {
			System.out.print("Error setting Ordering Context");
		}
		
	}
		
	/**
	 *  Initialize MaskInfo
	 * @return MaskInfo
	 */
	private static void setInputMaskInfo() {
		BaseAIF.maskInfo.setMaskPropertyPathList(null);
	}
	
	public HashMap<String, String> getProp() {
		return prop;
	}

	public void setProp(HashMap<String, String> prop) {
		this.prop = prop;
	}

	
}
