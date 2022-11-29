
import es.neoris.operations.BaseAIF;


/**
 * Launch classes for project LanzaProceso
 */

/**
 * @author Neoris
 *
 */
public class ExecuteAIF {

	private int m_execute = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args){

		if (args.length == 0) {
			System.out.println("Error. Input parameters missing.");
		}
		
		try {
			BaseAIF.startServices(args);
		}catch (Exception e) {
			System.out.println("Error al ejecutar la clase base");
			
		}
		
		
	}
	
	public ExecuteAIF() {}
	
}

