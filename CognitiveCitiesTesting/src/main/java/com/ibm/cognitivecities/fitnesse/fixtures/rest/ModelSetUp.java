package com.ibm.cognitivecities.fitnesse.fixtures.rest;

import resources.Constants;

public class ModelSetUp {
	public static String idName = Constants.DEFAULT_ID_NAME;

	public ModelSetUp() {
		idName = Constants.DEFAULT_ID_NAME;
	}
	
	public void setIdName(String s) {
		idName = s;
	}
		
	public void execute() {
		
	}
}
