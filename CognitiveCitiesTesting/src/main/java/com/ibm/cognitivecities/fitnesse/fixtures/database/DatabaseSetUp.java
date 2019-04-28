package com.ibm.cognitivecities.fitnesse.fixtures.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DatabaseSetUp {
	
	public static String host = "";
	public static String port = "";
	public static String connectionName = "";
	public static String url = "";
	public static String databaseName = "";
	public static String user = "";
	public static String password = "";
	public static boolean connected = false;
	public static Map<String,Connection> connections = new HashMap<String,Connection>();
	
	public DatabaseSetUp() {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Please include classpath where your DB2 Driver is located");
            e.printStackTrace();
        }		
	}
	
	public void setHost(String s) {
		host = s;
	}

	public void setPort(String p) {
		port = p;
	}

	public void setConnectionName(String s) {
		connectionName = s;
	}

	public void setDatabaseName(String s) {
		databaseName = s;
	}
	
	public void setUser(String s) {
		user = s;
	}

	public void setPassword(String s) {
		password = s;
	}
	
	public String connectionURL() {
		return url;
	}

	public boolean connectionOK() {
		return connected;
	}
	
	public void buildConnectionUrl() {
		url = String.format("jdbc:db2://%s:%s/%s",host,port,databaseName);		
	}
	
	public void execute() {
		buildConnectionUrl();
		try {
			if (connections.containsKey(connectionName)) 
				removeConnection(connectionName);
			Connection conn = DriverManager.getConnection(url, user, password);
			connections.put(connectionName, conn);
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeConnection(String name) {
		try {
			Connection conn = connections.get(name);
			if (conn != null) 
				conn.close();
			connections.remove(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		host = "";
		port = "";
		connectionName = "";
		url = "";
		databaseName = "";
		user = "";
		password = "";
		connected = false;
	}
}
