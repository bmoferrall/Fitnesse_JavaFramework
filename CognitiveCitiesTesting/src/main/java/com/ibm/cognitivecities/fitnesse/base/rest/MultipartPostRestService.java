package com.ibm.cognitivecities.fitnesse.base.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class MultipartPostRestService {
	String filepathKey;
	String filepathValue;
	String additionalFields;
	
	public MultipartPostRestService(String filepathKey, String filepathValue, String additionalFields) {
		this.filepathKey = filepathKey;
		this.filepathValue = filepathValue;
		this.additionalFields = additionalFields;
	}
	
	public HttpResponse callService(String serviceUrl, String userName, String password) {
		HttpResponse httpResponse = null;
		try {
			Base64 b64 = new Base64();
			String encoding = new String(b64.encode((userName + ":" + password).getBytes()));
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost mpartpost = new HttpPost(serviceUrl);
			
			mpartpost.addHeader("Authorization", String.format("Basic %s", encoding));
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			addCustomFields(builder);

			File f = new File(this.filepathValue);
			builder.addBinaryBody(this.filepathKey, new FileInputStream(f), 
									ContentType.APPLICATION_OCTET_STREAM, f.getName());

			HttpEntity multipart = builder.build();
			mpartpost.setEntity(multipart);
			CloseableHttpResponse response = httpClient.execute(mpartpost);
			httpResponse = new HttpResponse(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
			readResponse(response.getEntity(), httpResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpResponse;
	}
	
	public void addCustomFields(MultipartEntityBuilder builder) {
		if (this.additionalFields != null && this.additionalFields.length() > 0) {
			String[] fields = this.additionalFields.split(";");
			for (int i=0; i<fields.length; i++) {
				String[] keyValuePair = fields[i].split(":");
				if (keyValuePair[0] != null && keyValuePair[1] != null)
					builder.addTextBody(keyValuePair[0], keyValuePair[1], ContentType.TEXT_PLAIN);
			}

		}
	}
	
	public void readResponse(HttpEntity entity, HttpResponse httpResponse) throws Exception {
		StringBuilder returnedJson = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			returnedJson.append(line + "\n");
		}
		br.close();
		if (Utils.isNotNullAndNotEmpty(returnedJson.toString())) {
			httpResponse.setHttpResponseMessage(returnedJson.toString());
			httpResponse.setHttpResponseObjectCount(Utils.countObjectsInJsonResponse(returnedJson.toString()));
		}
	}

}
