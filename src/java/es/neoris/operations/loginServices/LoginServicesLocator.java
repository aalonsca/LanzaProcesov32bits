package es.neoris.operations.loginServices;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;

import es.neoris.operations.loginServices.interfaces.InterfaceLoginServices;
import es.neoris.operations.loginServices.interfaces.LoginServicesRemote;


public class LoginServicesLocator 
extends Service 
implements InterfaceLoginServices {

	private static final long serialVersionUID = 5953845399393838557L;

    private String m_address; 	// Use to get a proxy class for LoginServicesRemote
    private String m_WSDDserviceName;     // The WSDD service name defaults to the port name.
    private HashSet ports = null;

    
    /**
     * No-op constructor
     */
	public LoginServicesLocator() {
    }


	/**
	 * Constructor EngineConfiguratioin
	 * @param config
	 */
    public LoginServicesLocator(EngineConfiguration config) {
        super(config);
    }

    /**
     * Constructor wsdlLoc y namespace
     * @param wsdlLoc
     * @param sName
     * @throws ServiceException
     */
    public LoginServicesLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

 

    /**
     * Instantiate loginService
     */
    public LoginServicesRemote getLoginServices() throws ServiceException {
       URL endpoint;
       
        try {
            endpoint = new URL(m_address);
        }
        
        catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getLoginServices(endpoint);
    }

    /**
     * Instantiate LoginService through URL 
     */
    public LoginServicesRemote getLoginServices(URL portAddress) throws ServiceException {
    	
        try {
           LoginServicesBindingStub _stub = new LoginServicesBindingStub(portAddress, this);
            _stub.setPortName(getWSDDServiceName());
            return _stub;
        }
        catch (AxisFault e) {
            return null;
        }
    }

    
    /**
     * Proxy class
     * @param address
     */
    public void setLoginServicesEndpointAddress(String address) {
        m_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (InterfaceLoginServices.class.isAssignableFrom(serviceEndpointInterface)) {
                LoginServicesBindingStub _stub = new LoginServicesBindingStub(new URL(m_address), this);
                _stub.setPortName(getWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ASM7LoginServices".equals(inputPortName)) {
            return getLoginServices();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName(getWSDDServiceName(), "ASM7LoginServices");
    }


    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName(getWSDDServiceName(), "ASM7LoginServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
        
    	if ("ASM7LoginServices".equals(portName)) {
            setLoginServicesEndpointAddress(address);
        }
        else 
        { // Unknown Port Name
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }


    /**
     * Get proxy class
     */
    public String getAddress() {
        return m_address;
    }

    /**
     * Get namespace
     * @return
     */
    public String getWSDDServiceName() {
        return m_WSDDserviceName;
    }

    /**
     * Set namespace
     * @param name
     */
    public void setWSDDServiceName(String name) {
        m_WSDDserviceName = name;
    }


}
