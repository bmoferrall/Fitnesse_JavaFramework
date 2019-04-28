package com.ibm.cognitivecities.fitnesse.fixtures.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.simple.JSONObject;

import com.ibm.cognitivecities.fitnesse.base.database.JsonQueryResult;
import com.ibm.cognitivecities.fitnesse.base.database.QueryResult;
import com.ibm.cognitivecities.fitnesse.base.rest.Utils;


import resources.Messages;



public class GenericDatabaseFixture {
	public String connectionName = "";
	public String statementType = "";
	public String statementText = "";
	public String responseFormat = "";
	public String response = "";
	public String responseMessage = "";
	public int resultCount = 0;
	public String firstResultField = "";
	Connection conn;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;

	public GenericDatabaseFixture() {
		reset();
	}
	
	public void setConnectionName(String s) {
		connectionName = s;
		try {
			conn = DatabaseSetUp.connections.get(connectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStatementType(String s) {
		statementType = s;
	}
	
	public void setStatementText(String s) {
		statementText = s;
	}
	
	public void setResponseFormat(String s) {
		responseFormat = s;
	}
	
	public String getResponse() {
		return response;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void reset() {
		connectionName = "";
		statementType = "read";
		statementText = "";
		responseFormat = "json";
		response = "";
		responseMessage = "";
		firstResultField = "";
	}
	
	public void execute() {
		if (conn != null) {
			try {
				pstmt=conn.prepareStatement(statementText);
				if (statementType.equalsIgnoreCase("read")) {
					rs = pstmt.executeQuery();
					processQueryResponse(rs);
				} else if (statementType.equalsIgnoreCase("create") ||
						   statementType.equalsIgnoreCase("update") ||
						   statementType.equalsIgnoreCase("delete")) {
					pstmt.executeUpdate();
					responseMessage = "OK";
				} else {
					responseMessage = String.format(Messages.DB_UNSUPPORTED_STATEMENT_TYPE, statementType);
				}
			} catch (Exception e) {
				responseMessage = e.getMessage();
				e.printStackTrace();
			}
		} else {
			responseMessage = String.format(Messages.DB_INVALID_CONNECTION_NAME, connectionName);
		}
	}
	
	public void processQueryResponse(ResultSet rs) {
		QueryResult qr = null;
		
		if (responseFormat.equalsIgnoreCase("JSON"))
			qr = new JsonQueryResult(rs);
		else
			responseMessage = String.format(Messages.DB_UNSUPPORTED_RESPONSE_FORMAT, responseFormat);
		
		if (qr != null) {
			response = qr.getResult();
			responseMessage = qr.getResponseMessage();
			resultCount = qr.getResultCount();
			firstResultField = Utils.getFirstFieldFromJsonResponse(response);
		}
	}
	
	public int getResultCount() {
		return resultCount;
	}
	
	public String getFirstResultField() {
		return firstResultField;
	}
	
	
	public JSONObject getResponseMap() {
		return Utils.jsonAsObject(response);
	}

	
}
