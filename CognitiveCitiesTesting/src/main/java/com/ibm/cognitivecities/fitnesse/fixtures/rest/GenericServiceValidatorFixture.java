package com.ibm.cognitivecities.fitnesse.fixtures.rest;

import org.json.simple.JSONObject;

import com.ibm.cognitivecities.fitnesse.base.rest.DeleteRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.GetRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.HttpResponse;
import com.ibm.cognitivecities.fitnesse.base.rest.PatchRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.PostRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.PutRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.RestService;
import com.ibm.cognitivecities.fitnesse.base.rest.Utils;

import resources.Constants;
import resources.Messages;

public class GenericServiceValidatorFixture {
	public String serviceName;
	public String url;
	public String requestType;
	public String requestBody;
	public String customHeaders;
	HttpResponse response;
	public static String responseId = null;
	
	public GenericServiceValidatorFixture() {
		reset();
	}
	
	public void setRequestType(String type) {
		this.requestType = type;
	}
	
	public void setServiceName(String name) {
		this.serviceName = name;
	}
	
	public void setRequestBody(String body) {
		this.requestBody = body;
	}
	
	public void setCustomHeaders(String headers) {
		this.customHeaders = headers;
	}
	
	private String buildUrl() {
		return String.format(Constants.GENERIC_SERVICE_URL, 
							 ApplicationSetUp.protocol,
							 ApplicationSetUp.host,
							 ApplicationSetUp.port,
							 ApplicationSetUp.urlbase,
							 serviceName);
	}
	
	public int getResponseCode() {
		return response.getHttpResponseCode();
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getResponseId() {
		return responseId;
	}
	
	public JSONObject getResponseMap() {
		return Utils.jsonAsObject(response.getHttpResponseMessage());
	}
	
	public String getResponseMessage() {
		return response.getHttpResponseMessage();
	}
	
	public int getResultCount() {
		return response.getHttpResponseObjectCount();
	}
	
	public HttpResponse defaultServiceResponse() {
		return new HttpResponse(500,"ERROR");
	}
	
	public void execute() {
		RestService restService = null;
		response = defaultServiceResponse();
		try {
			url = buildUrl();
			if (requestType.equalsIgnoreCase("GET"))
				restService = new GetRestService(customHeaders);
			else if (requestType.equalsIgnoreCase("PUT"))
				restService = new PutRestService(customHeaders);
			else if (requestType.equalsIgnoreCase("POST"))
				restService = new PostRestService(customHeaders);
			else if (requestType.equalsIgnoreCase("DELETE"))
				restService = new DeleteRestService(customHeaders);
			else if (requestType.equalsIgnoreCase("PATCH"))
				restService = new PatchRestService(customHeaders);
			else
				response.setHttpResponseMessage(Messages.REST_ERROR_BAD_REQUEST_TYPE);

			if (restService != null) {
				response = restService.callService(requestBody, url, ApplicationSetUp.user, ApplicationSetUp.password, true);
			
				if (requestType.equalsIgnoreCase("POST") || requestType.equalsIgnoreCase("GET"))
					saveResponseId(customHeaders);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		requestType = "";
		requestBody = "";
		serviceName = "";
		customHeaders = "";
		response = defaultServiceResponse();
	}
	
	private void saveResponseId(String customHeaders) {
		String id = Utils.extractIdFromResponse(getResponseMessage(), customHeaders);
		if (id != null)
			responseId = id;
	}
}
