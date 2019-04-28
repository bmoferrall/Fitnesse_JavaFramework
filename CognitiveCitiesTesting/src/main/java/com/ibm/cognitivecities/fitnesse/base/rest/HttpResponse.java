package com.ibm.cognitivecities.fitnesse.base.rest;

public class HttpResponse {

	private int httpResponseCode;
	private int responseObjectCount;
	private String httpResponseMessage;
	
	public HttpResponse(int httpCode, String httpMessage) {
		this.httpResponseCode = httpCode;
		this.httpResponseMessage = httpMessage;
		this.responseObjectCount = 0;
	}
	
	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public String getHttpResponseMessage() {
		return httpResponseMessage;
	}

	public void setHttpResponseMessage(String httpResponseMessage) {
		this.httpResponseMessage = httpResponseMessage;
	}
	
	public int getHttpResponseObjectCount() {
		return responseObjectCount;
	}
	
	public void setHttpResponseObjectCount(int count) {
		this.responseObjectCount = count;
	}
	
}
