package com.ibm.cognitivecities.fitnesse.base.rest;

import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;

public class PostRestService extends RestService {

	public PostRestService(String customHeaders) {
		super(customHeaders);
	}
	
	public HttpResponse callService(String requestJson, String serviceURL, String username, String password, boolean isSecure)
			throws Exception {
		initParams("POST", requestJson, serviceURL, username, password, isSecure);
		return doRestServiceCall();
	}
	
	public void addBodyToRequest(HttpURLConnection conn) throws Exception {		
		BufferedOutputStream wr = new BufferedOutputStream(conn.getOutputStream());
		wr.write(requestJson.getBytes("UTF-8"));
		wr.flush();
		wr.close();
	}
	
	public HttpURLConnection initialiseHttpConnection() throws Exception {
		HttpURLConnection conn = initialiseHttpConnectionMain();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");		
		conn.setRequestProperty("Accept", "application/json");
		return conn;
	}
	
	public boolean isValidHttpCode(int responseCode) {
		return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_CREATED
				|| responseCode == HttpURLConnection.HTTP_NO_CONTENT;
	}
}
