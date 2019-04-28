package com.ibm.cognitivecities.fitnesse.base.database;

import java.sql.ResultSet;
import java.util.Map;


import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.json.java.OrderedJSONObject;

public class JsonQueryResult extends QueryResult {
	
	public JsonQueryResult(ResultSet rs) {
		super(rs);
	}
	
	public String getResult() {
		JSONArray jsonResult = getAllRowsAsJSONArray();
		return jsonResult.toString();
	}
	
	public JSONArray getAllRowsAsJSONArray(){
		JSONArray jsonArray = new JSONArray();
		
		while (next()) {
			jsonArray.add(getRowAsJSON());
		}
		return jsonArray;
	}
	
	public JSONObject getRowAsJSON() {
		Map<String, Object> row = this.dataset.get(cursor);
		JSONObject result = new OrderedJSONObject();
		
		for (Map.Entry<String, Object> entry : row.entrySet())
		{
			Object value = null;
			try {
				value = entry.getValue();
				result.put(entry.getKey(), value);	
			} catch (Exception e) {
				responseMessage = e.getMessage();
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
