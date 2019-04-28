package com.ibm.cognitivecities.fitnesse.scenarios.util;

import java.text.SimpleDateFormat;

public class TimeScenarios {
	String responseMessage = "";
	String response = "";
	
	public String timeUtilResponseMessage() {
		return responseMessage;
	}

	public String timeUtilResponse() {
		return response;
	}
	
	private void okResponse(String message) {
		responseMessage = "OK";
		response = message;
	}
	
	private void errorResponse(String message) {
		responseMessage = "KO";
		response = message;
	}

	public void pauseExecution(long delay) {
		try {
			if (delay > 0) 
				Thread.sleep(delay);
			okResponse("");
		} catch (InterruptedException e) {
			errorResponse(e.getMessage());
		}
	}
	
	public long getCurrentTimestamp() {
		okResponse("");
		return System.currentTimeMillis();
	}
	
	public String getFormattedCurrentTime(String format) {
		String formattedTime = "";
		if (format == null || format.trim().length() == 0) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		try {
			long currentTime = System.currentTimeMillis();
			SimpleDateFormat datetimeFormat = new SimpleDateFormat(format);
			formattedTime = datetimeFormat.format(currentTime);
			okResponse("");
		} catch (Exception e) {
			errorResponse(e.getMessage());
		}
		return formattedTime;
	}

}
