/**
 * 
 */
package es.neoris.operations.utils.bbdd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;



import es.neoris.operations.utils.interfaces.InterfaceDataBase;
import oracle.jdbc.OracleTypes;

/**
 * @author Neoris
 *
 */
public class OracleDB 
implements InterfaceDataBase {

 
	/**
	 * common properties to database connection
	 */
	private String DBDriver = "jdbc:oracle:thin:@";
	private static HashMap<String, Connection> connection = new HashMap<String, Connection>();


	/**
	 * no-op constructor
	 */
	public OracleDB() {
		/*no-op constructor*/
	}
	
	/**
	 * Generates an OracleDB object, opening the connection to the database
	 * @param strService
	 * @param prop
	 */
	public OracleDB(String strService, HashMap<String, String> prop) {
		
		try {
			connection = (HashMap<String, Connection>) openDBConnection(strService, prop, true);
		}catch(Exception e) {}
		
	}
	
	/**
	 * Open an Oracle db connection
	 */
	public Map<String, Connection> openDBConnection(String strService, HashMap<String, String> prop, Boolean debug)
			throws SQLException {

		if (debug) 
			System.out.println("Entering openDBConnection with value : " + strService);

		
		if (!"".equals(strService) && ("LAUNCHORDER".contains(strService) || "ALL".equals(strService))) {

			if (debug) 
				System.out.println("Opening ORACLE OMS...");

			Connection oraConexionOMS = null;
			Connection oraConexionPC = null;
			DBDriver = "jdbc:oracle:thin:@" + (String) prop.get("JDBC_DB") + ":" + (String) prop.get("JDBC_PORT") + ":" + (String) prop.get("DB_NAME");

			//Open db connection using OMS user
			try {

				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				oraConexionOMS = DriverManager.getConnection(DBDriver, (String) prop.get("DB_USER_OMS"), (String) prop.get("DB_PASS_OMS"));

				connection.put("OMS", oraConexionOMS);
				if (debug) System.out.println("ORACLE OMS connection opened");


			} catch (SQLException sqle) {
				
				if (debug) System.out.println("ERROR Opening Oracle OMS DB connection FAILED: " + sqle.toString());
				throw new SQLException("Error opening Oracle db connection: " +  (String) prop.get("DB_NAME") + ":" + (String) prop.get("JDBC_PORT"));
			}
			

			//Open db connection using PC user
			try {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				oraConexionPC = DriverManager.getConnection(DBDriver, (String) prop.get("DB_USER_PC"), (String) prop.get("DB_PASS_PC"));

				connection.put("PC", oraConexionPC);
				if (debug) System.out.println("ORACLE PC connection opened");
				
			} catch (SQLException sqle) {
				
				if (debug) System.out.println( "ERROR Opening Oracle PC DB connection FAILED: " + sqle.toString());
				throw new SQLException("Error opening Oracle db connection: " +  (String) prop.get("DB_NAME") + ":" + (String) prop.get("JDBC_PORT"));
			}
			
		}
		
		return connection;
	}

	/**
	 * Close a db connection (service -> Name of connection to close)
	 */
	public void closeDBConnection(HashMap<String, Connection> connection, String service, Boolean debug)
			throws SQLException {
		
		//Try to close OMS a PC connection
		if ((connection.get(service) != null) && (!connection.get(service).isClosed())) 
		{ 
			try {
				
				connection.get(service).close();
			} catch (SQLException sqle) {
				if (debug) System.out.println( "ERROR Closing Oracle DB connection FAILED: " + sqle.toString());
				throw new SQLException("Error closing db connection");
			}
		}
		
	};


	/**
	 * Close all the opened db connections
	 * @param connection
	 * @param debug
	 * @throws SQLException
	 */
	public void closeAllDBConnection()
			throws SQLException {

		System.out.println( "Entering closeAllDBConnection");

		for (Connection conn : connection.values()) {
			try {
				if (!conn.isClosed())
					conn.close();
			}catch(Exception e) {
				System.out.println("An error ocurred trying to close db connection:");
				throw new SQLException("Error closing db connection (1)");
			}
		}

		System.out.println("Connections closed");
		
	}

	
	/**
	 * Execute a SELECT query
	 */
	public ResultSet execSQL(Connection connection, String sql) 
		throws Exception{

		PreparedStatement sqlQuery = null;
		ResultSet result = null;
		
		try {
			
			sqlQuery = (PreparedStatement) (connection.prepareStatement(sql));
			result = sqlQuery.executeQuery();
		
		}catch(SQLException e) {
			System.out.println("ERROR executing query: " + e.toString());
			throw new Exception("ERROR executing query: " + e.toString());
		}
		
		return result;
	
	}


	/**
	 * Execute a PLSQL procedure. String proc must contain the call to the procedure with an ? for each input parameter
	 *    Example:   proc => createOrder(?, ?, ?) 
	 *                             where first ? is Customer ID (Integer)
	 *                                  second ? is Order Type  (String)
	 *                                   third ? is current date (Date)    
	 */
	public ResultSet[] execProc(Connection connection, String proc, HashMap<String, ?> input, HashMap<String, ?> output) 
			throws Exception{

		CallableStatement sqlQuery = null;
		ResultSet[] response = null;
		Integer pos = 0;
		Integer posOut = 0;
		
		if (output.entrySet().size() > 0) {
			response = new ResultSet[output.entrySet().size()];
		}else{
			response = new ResultSet[1];
		}
		
		try {
		
			sqlQuery = connection.prepareCall("{ call " + proc + " }");
	
			// Fill the input parameters
			if (input != null) {
	
				for (Map.Entry<String, ?> entry : input.entrySet()) {
					
					if(entry.getKey() == dataTypes.STRING.toString()) {
						sqlQuery.setString(++pos, (String) entry.getValue());
						
					}else if (entry.getKey() == dataTypes.INTEGER.toString()) {
						sqlQuery.setInt(++pos, (Integer) entry.getValue());
						
					}else if (entry.getKey() == dataTypes.DATE.toString()) {
						sqlQuery.setDate(++pos, (Date) entry.getValue());
						
					}
				}
			}
			
			
			// Register the output parameters
			if (output != null) {
	
				// Position of the first output parameter
				posOut = pos;
	
				for (Map.Entry<String, ?> entry : output.entrySet()) {
					
					if(entry.getKey() == dataTypes.STRING.toString()) {
						sqlQuery.registerOutParameter(++pos, OracleTypes.VARCHAR);
						
					}else if (entry.getKey() == dataTypes.INTEGER.toString()) {
						sqlQuery.registerOutParameter(++pos, OracleTypes.DOUBLE);
						
					}else if (entry.getKey() == dataTypes.DATE.toString()) {
						sqlQuery.registerOutParameter(++pos, OracleTypes.DATE);
						
					}else if (entry.getKey() == dataTypes.CURSOR.toString()) {
						sqlQuery.registerOutParameter(++pos, OracleTypes.CURSOR);
					}
				}
			}
			
			//Execute the procedure
			sqlQuery.execute();
			
			if (output != null) {
				ResultSet result = null;
			
				for (int i = 0; i < response.length; i++) {
					result = (ResultSet) sqlQuery.getObject(++posOut);
					response[i] = result;
				}
			}
		
		}catch(Exception e) {
			System.out.println("ERROR executing proc: " + e.toString());
			throw new Exception("ERROR executing proc: " + e.toString());
		}
		
		return response;
	}

	
	// Getters and setters
	public String getDBDriver() {
		return DBDriver;
	}

	public void setDBDriver(String dBDriver) {
		DBDriver = dBDriver;
	}

	public static HashMap<String, Connection> getConnection() {
		return connection;
	}

	public static void setConnection(HashMap<String, Connection> connection) {
		OracleDB.connection = connection;
	}

}
