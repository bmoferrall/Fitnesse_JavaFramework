package com.ibm.cognitivecities.fitnesse.base.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import resources.Constants;

import com.ibm.cognitivecities.fitnesse.fixtures.rest.ApplicationSetUp;
import com.ibm.cognitivecities.fitnesse.scenarios.rest.GenericServiceScenarios;




public class GenericServiceTestsWatson {
	@Before
	public void SetUp() {
		ApplicationSetUp.host = "9kpzic.internetofthings.ibmcloud.com";
		ApplicationSetUp.urlbase = "/api/v0002";
		ApplicationSetUp.user = "a-9kpzic-cfbqvnuh2h";
		ApplicationSetUp.password = "T-LN4Nd8onlBKol8s_";
	}
	
	
	@Test
	public void createDraftSchemaTest() {
		GenericServiceScenarios scenario = new GenericServiceScenarios();
		scenario.callMultipartPostService("/draft/schemas", "schemaFile", Constants.TESTFILES_PATH + "testSchema_LI.json", "name:testSchema_LI");
		String id = scenario.serviceResponseId();
		assertEquals(scenario.serviceResponseCode(),201);
		
		if (id != null) {
			scenario.callDeleteService(String.format("/draft/schemas/%s",id), "");
			assertEquals(scenario.serviceResponseCode(),204);
		}
	}

	@Test
	public void deviceInstanceLifecycleTest_Scenario() {
		String deviceType = "MyTestDeviceType";
		String deviceId = "MyTestDevice1";
		String deviceTypeDescription = "My Test Device Description";
		String deviceToken = "Pa88w0rd";
		String deviceUpdateBody = "{ \"deviceInfo\": { \"serialNumber\": \"12345\" } }";
		String deviceTypeBody = String.format("{\"id\": \"%s\",\"description\": \"%s\"}", deviceType, deviceTypeDescription);
		String deviceInstanceBody = String.format("{ \"deviceId\": \"%s\", \"authToken\": \"%s\" }", deviceId, deviceToken);

		GenericServiceScenarios scenario = new GenericServiceScenarios();
		
		scenario.callCreateService("/device/types", deviceTypeBody, "");
		assertEquals(scenario.serviceResponseCode(),201);

		scenario.callCreateService(String.format("/device/types/%s/devices",deviceType), deviceInstanceBody, "");
		assertEquals(scenario.serviceResponseCode(),201);

		scenario.callReadService("/device/types/MyTestDeviceType/devices", "");
		assertEquals(scenario.serviceResponseCode(),200);
		assertTrue(scenario.serviceResponse().contains(String.format("\"deviceId\":\"%s\"",deviceId)));
		
		scenario.callUpdateService(String.format("/device/types/%s/devices/%s", deviceType, deviceId), deviceUpdateBody, "");
		assertEquals(scenario.serviceResponseCode(),200);

		scenario.callReadService("/device/types/MyTestDeviceType/devices", "");
		assertEquals(scenario.serviceResponseCode(),200);
		assertTrue(scenario.serviceResponse().contains("\"serialNumber\":\"12345\""));
		
		scenario.callDeleteService(String.format("/device/types/%s/devices/%s",deviceType, deviceId), "");
		assertEquals(scenario.serviceResponseCode(),204);
		
		scenario.callDeleteService(String.format("/device/types/%s",deviceType), "");
		assertEquals(scenario.serviceResponseCode(),204);
	}

}
