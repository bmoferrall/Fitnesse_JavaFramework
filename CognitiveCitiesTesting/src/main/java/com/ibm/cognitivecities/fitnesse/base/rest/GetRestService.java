package com.ibm.cognitivecities.fitnesse.base.rest;

import java.net.HttpURLConnection;

public class GetRestService extends RestService {

	public GetRestService(String customHeaders) {
		super(customHeaders);
	}
	
	public HttpResponse callService(String requestJson, String serviceURL, String username, String password, boolean isSecure)
			throws Exception {
		initParams("GET", requestJson, serviceURL, username, password, isSecure);
		return doRestServiceCall();
	}
	
	public HttpURLConnection initialiseHttpConnection() throws Exception {
		HttpURLConnection conn = initialiseHttpConnectionMain();
		conn.setRequestProperty("Accept", "application/json");
		return conn;
	}
	
	public void addBodyToRequest(HttpURLConnection conn) throws Exception {		
	}
	
	public boolean isValidHttpCode(int responseCode) {
		return responseCode == HttpURLConnection.HTTP_OK;
	}
}
