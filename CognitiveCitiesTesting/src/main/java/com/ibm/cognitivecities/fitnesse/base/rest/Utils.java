package com.ibm.cognitivecities.fitnesse.base.rest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ibm.cognitivecities.fitnesse.fixtures.rest.ModelSetUp;

import resources.Constants;



public class Utils {

	public static boolean isNotNullAndNotEmpty(String stringValue) {
		return !isNullOrEmpty(stringValue);
	}

	public static boolean isNullOrEmpty(String stringValue) {
		return stringValue == null || stringValue.isEmpty();
	}

	public static String getDurationAsString(long batchDurationMs) {
		long milliseconds = batchDurationMs % 1000;
		long seconds = batchDurationMs / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		String executionTime = days + " days, " + hours % 24 + " hr : " + minutes % 60 + " min : " + seconds % 60 + " s : " + milliseconds + " ms";
		
		return executionTime;
	}
	
	public static int countObjectsInJsonResponse(String json) {
		JSONParser parser = new JSONParser();
		int itemCount = 0;
		
		try {
			if (json.startsWith("[")) {
				JSONArray jsonArray = (JSONArray) parser.parse(json);
				itemCount = jsonArray.size();
			} else if (json.startsWith("{")) {
				itemCount = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemCount;
	}
	
	public static String getFieldFromJsonResponse(String json, String fieldName) {
		String fieldValue = null;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			if (json.startsWith("[")) {
				JSONArray jsonArray = (JSONArray) parser.parse(json);
				if (jsonArray.size() > 0) {
					jsonObject = (JSONObject) jsonArray.get(0);					
				}
			} else if (json.startsWith("{")) {
				jsonObject = (JSONObject) parser.parse(json);
			}
			
			if (jsonObject != null && jsonObject.size() > 0) {
				Object val = jsonObject.get(fieldName);
				if (val instanceof String) {
					fieldValue = (String) val;
				} else {
					fieldValue = val.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fieldValue;
	}
	
	public static String extractIdFromResponse(String response, String customHeaders) {
		String responseId = null;
		if (!customHeaders.contains(Constants.DISABLE_IDSAVE_HEADER) && response != null && response.length() > 0) {
			String id = Utils.getFieldFromJsonResponse(response, ModelSetUp.idName);
			if (id != null && id.length() > 0 && !id.contentEquals("0")) {
				responseId = id;
			}
		}
		return responseId;
	}
	
	public static JSONObject jsonAsObject(String json) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			if (json != null && json.startsWith("[")) {
				JSONArray jsonArray = (JSONArray) parser.parse(json);
				if (jsonArray.size() > 0) {
					jsonObject = (JSONObject) jsonArray.get(0);					
				}
			} else if (json != null && json.startsWith("{")) {
				jsonObject = (JSONObject) parser.parse(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static String getFirstFieldFromJsonResponse(String json) {
		String fieldValue = null;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			if (json.startsWith("[")) {
				JSONArray jsonArray = (JSONArray) parser.parse(json);
				if (jsonArray.size() > 0) {
					jsonObject = (JSONObject) jsonArray.get(0);					
				}
			} else if (json.startsWith("{")) {
				jsonObject = (JSONObject) parser.parse(json);
			}
			
			if (jsonObject != null && jsonObject.size() > 0) {
				String key = (String)jsonObject.keySet().iterator().next();
				Object val = jsonObject.get(key);
				if (val instanceof String) {
					fieldValue = (String) val;
				} else {
					fieldValue = val.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fieldValue;
	}


}
