package com.ibm.cognitivecities.fitnesse.fixtures.database;

import java.sql.Connection;
import java.util.Map;


public class DatabaseTearDown {
	
	public DatabaseTearDown() {
		try {
			Map<String,Connection> connections = DatabaseSetUp.connections;
			for (String connectionName : connections.keySet()) {
				if (connections.get(connectionName) != null)
					connections.get(connectionName).close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
