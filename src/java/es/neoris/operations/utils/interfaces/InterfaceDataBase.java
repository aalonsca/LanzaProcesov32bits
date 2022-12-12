package es.neoris.operations.utils.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface InterfaceDataBase {

	public enum dataTypes {
		STRING, 
		INTEGER, 
		DATE, 
		CURSOR		
	}
	
	/**
	 * Open db connection
	 * @param strService  --> Name of the connection to open
	 * @param prop --> parameters necessary to open the db connection
	 * @param debug --> log active (true) or not (false)
	 * @return  --> Map<String, Connection>
	 * @throws SQLException
	 */
	public Map<String, Connection> openDBConnection(String strService, HashMap<String, String> prop, Boolean debug) throws SQLException;
	
	/**
	 * Close a db connection
	 * @param connection --> Map<String, Connection>
	 * @param debug --> log active (true) or not (false)
	 * @throws SQLException
	 */
	public void closeDBConnection(HashMap<String, Connection> connection, String service, Boolean debug) throws SQLException;
	
	/**
	 * Close all opened db connections
	 * @throws SQLException
	 */
	public void closeAllDBConnection() throws SQLException;
	
	/**
	 * Executes SELECT sql through the connection
	 * @param connection --> db connection
	 * @param sql --> SELECT to execute
	 * @return Resultset 
	 */
	public ResultSet execSQL(Connection connection, String sql) throws Exception;
	
	
	/**
	 * Execute PLSQL through the connection 
	 * @param connection --> db connection
	 * @param proc --> procedure to execute. Must have ? for each input param
	 * @param input --> input params <type, value> where type could be: string, integer, date, cursor
	 * @param output --> output params <type, value> where type could be: string, integer, date, cursor
	 * @return Resulset
	 */
	public ResultSet[] execProc(Connection connection, String proc, HashMap<String, ?> input, HashMap<String, ?> output) throws Exception;
	
	
	
	
}
