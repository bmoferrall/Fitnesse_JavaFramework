package com.ibm.cognitivecities.fitnesse.fixtures.rest;

public class ApplicationSetUp {
	
	public static String protocol = "https";
	public static String port = "443";
	public static String host = "";
	public static String urlbase = "";
	public static String user = "";
	public static String password = "";
	
	public ApplicationSetUp() {
		
	}
	
	public void setProtocol(String s) {
		protocol = s;
	}
	
	public void setUrlbase(String s) {
		urlbase = s;
	}
	
	public void setPort(String s) {
		port = s;
	}

	public void setHost(String s) {
		host = s;
	}

	public void setUser(String s) {
		user = s;
	}

	public void setPassword(String s) {
		password = s;
	}
	

	public String protocol() {
		return protocol;
	}
	
	public String urlbase() {
		return urlbase;
	}
	
	public String host() {
		return host;
	}
	
	public String port() {
		return port;
	}
	
	public String user() {
		return user;
	}
	
	public void execute() {
		
	}
	
}
