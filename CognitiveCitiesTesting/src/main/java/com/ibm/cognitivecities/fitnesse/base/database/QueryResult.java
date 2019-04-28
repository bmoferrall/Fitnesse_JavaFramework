package com.ibm.cognitivecities.fitnesse.base.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import resources.Messages;

public abstract class QueryResult {
	ResultSet resultSet;
	List<Map<String,Object>> dataset;
	int cursor;
	Map<String, String> schema;
	protected String responseMessage;
	
	public QueryResult(ResultSet rs) {
		cursor = -1;
		responseMessage = Messages.DB_QUERY_RESPONSE_OK;
		dataset = new ArrayList<Map<String,Object>>();
		schema = new HashMap<String,String>();
		try {
			populateDatasetFromResultSet(rs);
		} catch (Exception e) {
			responseMessage = e.getMessage();
			e.printStackTrace();
		}
	}
	
	public abstract String getResult();
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public boolean next()  {
		if (this.dataset == null) {
			return false;
		}
		if (this.cursor < this.dataset.size() - 1) {
			this.cursor++;
			return true;
		}         
		return false;
	}
	
	public void populateDatasetFromResultSet(ResultSet resultSet) throws SQLException {
		if (resultSet == null || resultSet.getMetaData() == null) {
			return;
		}
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			String name = resultSet.getMetaData().getColumnLabel(i);
			String type = resultSet.getMetaData().getColumnTypeName(i);
			schema.put(name, type);
		}
		
		while (resultSet.next()) {
			Map<String, Object> row = new LinkedHashMap<String, Object>(resultSet.getMetaData().getColumnCount());
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				String name = resultSet.getMetaData().getColumnLabel(i);
				Object value = this.readFromDB(resultSet, i);
				row.put(name, value);
			}
			dataset.add(row);
		}
	}
	
	private Object readFromDB(ResultSet resultSet, int column) throws SQLException {
		String type = resultSet.getMetaData().getColumnTypeName(column);
		
		resultSet.getMetaData().getColumnType(column);
		
		if (type.equals("DATE")) {
			return resultSet.getDate(column);
		} else if (type.equals("TIME")) {
			return resultSet.getTime(column);
		} else if (type.equals("TIMESTAMP")) {
			return resultSet.getTimestamp(column);
		} else if (type.startsWith("\"DB2GSE\".\"ST_")) {
			return resultSet.getString(column);
		} else if (type.equals("XML")) {
			return resultSet.getString(column);
		} else if (type.equals("CLOB")) {
			return resultSet.getString(column);
		} else if (type.equals("BLOB")) {
			return resultSet.getBinaryStream(column);
		} else {
			return resultSet.getObject(column);
		}
	}
	
	public int getResultCount() {
		return dataset.size();
	}

}
