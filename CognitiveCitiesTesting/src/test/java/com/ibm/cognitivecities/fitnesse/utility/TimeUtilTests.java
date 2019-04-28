package com.ibm.cognitivecities.fitnesse.utility;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ibm.cognitivecities.fitnesse.base.TestBase;
import com.ibm.cognitivecities.fitnesse.scenarios.util.TimeScenarios;


public class TimeUtilTests extends TestBase {

	@Before
	public void applicationSetUp() {
	}
	
	@After
	public void TearDown() {
	}
	
	@Test
	public void timeDelayTest() {
		TimeScenarios ts = new TimeScenarios();
		long intervalInMs = 10000;
		long startTime = System.nanoTime();
		long durationInMs;
		
		ts.pauseExecution(intervalInMs);
		durationInMs = (System.nanoTime() - startTime)/1000000; 
		assertTrue(durationInMs >= intervalInMs-100);
	}
	
	@Test
	public void currentTimeFormatTest() {
		TimeScenarios ts = new TimeScenarios();
		System.out.println(ts.getFormattedCurrentTime(""));
		assertEquals(ts.timeUtilResponseMessage(),"OK");
	}

}
