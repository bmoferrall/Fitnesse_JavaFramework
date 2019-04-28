package com.ibm.cognitivecities.fitnesse.scenarios.rest;

import org.json.simple.JSONObject;

import com.ibm.cognitivecities.fitnesse.base.rest.DeleteRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.GetRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.HttpResponse;
import com.ibm.cognitivecities.fitnesse.base.rest.MultipartPostRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.PatchRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.PostRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.PutRestService;
import com.ibm.cognitivecities.fitnesse.base.rest.RestService;
import com.ibm.cognitivecities.fitnesse.base.rest.Utils;
import com.ibm.cognitivecities.fitnesse.fixtures.rest.ApplicationSetUp;

import resources.Constants;

public class GenericServiceScenarios {
	public String url;
	RestService restService;
	HttpResponse response;
	String responseId = null;

	public void callReadService(String serviceName, String customHeaders) {
		response = defaultServiceResponse();
		restService = new GetRestService(customHeaders);
		url = buildUrl(serviceName);
		callService(url, "");
		saveResponseId(customHeaders);
	}
	
	public void callCreateService(String serviceName, String requestBody, String customHeaders) {
		response = defaultServiceResponse();
		restService = new PostRestService(customHeaders);
		url = buildUrl(serviceName);
		callService(url, requestBody);
		saveResponseId(customHeaders);
	}

	public void callUpdateService(String serviceName, String requestBody, String customHeaders) {
		response = defaultServiceResponse();
		restService = new PutRestService(customHeaders);
		url = buildUrl(serviceName);
		callService(url, requestBody);
	}
	
	public void callDeleteService(String serviceName, String customHeaders) {
		response = defaultServiceResponse();
		restService = new DeleteRestService(customHeaders);
		url = buildUrl(serviceName);
		callService(url, "");
	}

	public void callPatchService(String serviceName, String requestBody, String customHeaders) {
		response = defaultServiceResponse();
		restService = new PatchRestService(customHeaders);
		url = buildUrl(serviceName);
		callService(url, requestBody);
	}	

	public void callMultipartPostService(String serviceName, String filepathKey, String filepathValue, String additionalFields) {
		response = defaultServiceResponse();
		MultipartPostRestService restService = new MultipartPostRestService(filepathKey, filepathValue, additionalFields);
		url = buildUrl(serviceName);
		response = restService.callService(url, ApplicationSetUp.user, ApplicationSetUp.password);
		saveResponseId("");
	}
	
	public void callService(String url, String requestBody) {
		try {
			response = restService.callService(requestBody, url, ApplicationSetUp.user, ApplicationSetUp.password, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int serviceResponseCode() {
		return response.getHttpResponseCode();
	}
	
	public String serviceResponse() {
		return response.getHttpResponseMessage();
	}
	
	public String serviceResponseId() {
		return responseId;
	}
	
	public JSONObject serviceResponseMap() {
		return Utils.jsonAsObject(response.getHttpResponseMessage());
	}
	
	public int serviceResultCount() {
		return response.getHttpResponseObjectCount();
	}
	
	public String serviceUrl() {
		return url;
	}
	
	public HttpResponse defaultServiceResponse() {
		return new HttpResponse(500,"ERROR");
	}

	private String buildUrl(String serviceName) {
		return String.format(Constants.GENERIC_SERVICE_URL, 
							 ApplicationSetUp.protocol,
							 ApplicationSetUp.host,
							 ApplicationSetUp.port,
							 ApplicationSetUp.urlbase,
							 serviceName);
	}
	
	private void saveResponseId(String customHeaders) {
		String id = Utils.extractIdFromResponse(serviceResponse(), customHeaders);
		if (id != null)
			responseId = id;
	}
		
}
