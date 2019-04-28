package com.ibm.cognitivecities.fitnesse.base.rest;

import org.junit.Before;

import com.ibm.cognitivecities.fitnesse.base.TestBase;
import com.ibm.cognitivecities.fitnesse.fixtures.rest.ApplicationSetUp;


public class SopServiceTests {

	@Before
	public void applicationSetUp() {
		ApplicationSetUp.host = TestBase.webHost;
		ApplicationSetUp.urlbase = "/ibm/ioc/api";
		ApplicationSetUp.user = TestBase.webUser;
		ApplicationSetUp.password = TestBase.webPassword;
	}

}
