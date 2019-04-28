package com.ibm.cognitivecities.fitnesse.base.rest;

import java.net.HttpURLConnection;

public class DeleteRestService extends RestService {
	
	public DeleteRestService(String customHeaders) {
		super(customHeaders);
	}
	
	public HttpResponse callService(String requestJson, String serviceURL, String username, String password, boolean isSecure)
			throws Exception {
		initParams("DELETE", requestJson, serviceURL, username, password, isSecure);
		return doRestServiceCall();
	}
	
	public HttpURLConnection initialiseHttpConnection() throws Exception {
		HttpURLConnection conn = initialiseHttpConnectionMain();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");		
		conn.setRequestProperty("Accept", "application/json");
		return conn;
	}
	
	public void addBodyToRequest(HttpURLConnection conn) throws Exception {		
	}
	
	public boolean isValidHttpCode(int responseCode) {
		return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
	}
}
