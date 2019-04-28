package com.ibm.cognitivecities.fitnesse.fixtures.rest;

import resources.Constants;

public class ModelTearDown {
	
	public ModelTearDown() {
		resetDefaults();
	}

	public void resetDefaults() {
		ModelSetUp.idName = Constants.DEFAULT_ID_NAME;
	}
}
