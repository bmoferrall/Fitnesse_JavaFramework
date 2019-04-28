package com.ibm.cognitivecities.fitnesse.base.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.codec.binary.Base64;

import resources.Constants;

public abstract class RestService {
	protected String requestType;
	protected String requestJson;
	protected String serviceURL;
	protected String username;
	protected String password;
	protected boolean isSecure;
	protected String customHeaders;
	
	public Logger log;

	public RestService(String headers) {
		customHeaders = headers;
		log = Logger.getLogger(RestService.class);
		log.setLevel(Constants.LOG_LEVEL);
		BasicConfigurator.configure();
	}
	
	protected void initParams(String requestType, String requestJson, String serviceURL, String username, String password, boolean isSecure) {
		this.requestType = requestType;
		this.requestJson = requestJson;
		this.serviceURL = serviceURL;
		this.username = username;
		this.password = password;
		this.isSecure = isSecure;
	}
	
	public abstract HttpResponse callService(String requestJson, String serviceURL, String username, String password, boolean isSecure) throws Exception;

	public abstract void addBodyToRequest(HttpURLConnection conn) throws Exception;
	
	public abstract HttpURLConnection initialiseHttpConnection() throws Exception;
	
	public abstract boolean isValidHttpCode(int responseCode);
	
	protected HttpURLConnection initialiseHttpConnectionMain() throws Exception {
		Base64 b64 = new Base64();
		String encoding = new String(b64.encode((username + ":" + password).getBytes()));

		if (isSecure)
			setSSL();
		URL url = new URL(serviceURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(requestType);
		conn.setRequestProperty("Authorization", String.format("Basic %s", encoding));
		addCustomHeaders(conn);
		
		return conn;
	}
	
	protected void addCustomHeaders(HttpURLConnection conn) {
		if (customHeaders != null && customHeaders.length() > 0) {
			String[] headers = customHeaders.split(";");
			for (int i=0; i<headers.length; i++) {
				String[] keyValuePair = headers[i].split(":");
				if (keyValuePair[0] != null && keyValuePair[1] != null)
					conn.setRequestProperty(keyValuePair[0], keyValuePair[1]);
			}
		}
	}
	
	protected HttpResponse doRestServiceCall() throws Exception {

		long startTime = System.currentTimeMillis();
		HttpResponse httpResponse = null;
		HttpURLConnection conn = initialiseHttpConnection();

		if (log.isDebugEnabled()) {
			log.debug(new StringBuilder().append("\nSending ").append(requestType).append(" request to dme service URL : ").append(serviceURL).toString());
		}

		addBodyToRequest(conn);

		httpResponse = handleHttpResponse(conn);
		conn.disconnect();
		
		long endTime = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug(new StringBuilder().append(requestType)
										 .append(" service call lasted : ")
										 .append(Utils.getDurationAsString(endTime - startTime))
										 .toString());
		}
		return httpResponse;
	}

	protected HttpResponse handleHttpResponse(HttpURLConnection conn) throws Exception {
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		HttpResponse httpResponse = new HttpResponse(responseCode, responseMessage);
		StringBuilder returnedJson = new StringBuilder();
		
		if (isValidHttpCode(responseCode)) {
			handleValidHttpResponse(conn, httpResponse, returnedJson);
		} else if (!isErrorHttpCode(responseCode)) {
			handleInvalidHttpResponse(conn, httpResponse, returnedJson);
		} else {
			//handleErrorHttpResponse(conn, httpResponse, returnedJson);
			handleInvalidHttpResponse(conn, httpResponse, returnedJson);
		}
		
		if (log.isDebugEnabled()) {
			String message = String.format("The service returned the following response : [code=%d], [body=%s]", 
											responseCode, 
											returnedJson.toString());
			log.debug(message);
		}
		return httpResponse;
	}
	
	public void handleValidHttpResponse(HttpURLConnection conn, HttpResponse httpResponse, StringBuilder returnedJson) throws Exception {
		readResponse(conn, httpResponse, returnedJson);
		if (Utils.isNotNullAndNotEmpty(returnedJson.toString())) {
			httpResponse.setHttpResponseMessage(returnedJson.toString());
			httpResponse.setHttpResponseObjectCount(Utils.countObjectsInJsonResponse(returnedJson.toString()));
		}
	}

	public void readResponse(HttpURLConnection conn, HttpResponse httpResponse, StringBuilder returnedJson) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			returnedJson.append(line + "\n");
		}
		br.close();
	}
	


	public void handleInvalidHttpResponse(HttpURLConnection conn, HttpResponse httpResponse, StringBuilder returnedJson) throws Exception {
		String responseError = getErrorMessage(conn);
		
		if (log.isDebugEnabled()) {
			log.debug(responseError);
		}
		httpResponse.setHttpResponseMessage(responseError);

		String errorMessage = String.format("Response error [code: %d], [message: %s], [content: %s]", 
											httpResponse.getHttpResponseCode(), 
											responseError, 
											httpResponse.getHttpResponseMessage());
		log.error(errorMessage);
	}

	public void handleErrorHttpResponse(HttpURLConnection conn, HttpResponse httpResponse, StringBuilder returnedJson) throws Exception {
		String errorMessage = String.format("Response error [code: %d], [message: %s]", 
											httpResponse.getHttpResponseCode(), 
											httpResponse.getHttpResponseMessage());
		log.error(errorMessage);
	}
	
	public boolean isErrorHttpCode(int responseCode) {
		return responseCode == HttpURLConnection.HTTP_NOT_FOUND ||
				responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR;		
	}
	
	protected String getErrorMessage(HttpURLConnection conn) {
		String rawErrorMessage = getHttpErrorMessage(conn);
		String parsedErrorMessage = "";

		if (rawErrorMessage != "") {
			parsedErrorMessage = getMessageFromRawJson(rawErrorMessage);
		}

		return parsedErrorMessage;
	}

	protected String getMessageFromRawJson(String rawErrorMessage) {
		String parsedMessage = "";
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(rawErrorMessage);
			parsedMessage = (String) jsonObject.get("message");
		}
		catch (ParseException e) {
			log.error("Error parsing the Json:" + rawErrorMessage);
			log.error(e.getMessage(), e);
		}
		return parsedMessage;
	}

	protected String getHttpErrorMessage(HttpURLConnection conn) {
		String errorMessage = "";

		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
			String inputLine = null;
			StringBuffer responseContent = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseContent.append(inputLine);
			}
			in.close();
			errorMessage = responseContent.toString();
		}
		catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return errorMessage;
	}

	protected void setSSL() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = null;

		try {
			sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}
		catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		catch (KeyManagementException e) {
			log.error(e.getMessage(), e);
		}
	}

}
