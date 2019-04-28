package com.ibm.cognitivecities.fitnesse.scenarios.database;

import com.ibm.cognitivecities.fitnesse.fixtures.database.GenericDatabaseFixture;

public class DatabaseScenarios {
	
	GenericDatabaseFixture gbf = null;

	public void executeRead(String connectionName, String responseFormat, String sqlStmt) {
		gbf = new GenericDatabaseFixture();
		gbf.setConnectionName(connectionName);
		gbf.setStatementType("read");
		gbf.setResponseFormat(responseFormat);
		gbf.setStatementText(sqlStmt);
		gbf.execute();
	}

	public void executeCreate(String connectionName, String sqlStmt) {
		gbf = new GenericDatabaseFixture();
		gbf.setConnectionName(connectionName);
		gbf.setStatementType("create");
		gbf.setStatementText(sqlStmt);
		gbf.execute();
	}

	public void executeUpdate(String connectionName, String sqlStmt) {
		gbf = new GenericDatabaseFixture();
		gbf.setConnectionName(connectionName);
		gbf.setStatementType("update");
		gbf.setStatementText(sqlStmt);
		gbf.execute();
	}

	public void executeDelete(String connectionName, String sqlStmt) {
		gbf = new GenericDatabaseFixture();
		gbf.setConnectionName(connectionName);
		gbf.setStatementType("delete");
		gbf.setStatementText(sqlStmt);
		gbf.execute();
	}
	
	public String databaseResponse() {
		String response = "";
		try {
			response = gbf.getResponse();
		} catch (Exception e) {
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
	
	public String databaseResponseMessage() {
		return gbf.getResponseMessage();
	}
	
	public int databaseResultCount() {
		return gbf.getResultCount();
	}
	
	public String databaseFirstResultField() {
		return gbf.getFirstResultField();
	}

}
 