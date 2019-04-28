package com.ibm.cognitivecities.fitnesse.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import resources.Constants;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestBase {
    public static String webHost;
    public static String webUser;
    public static String webPassword;
    public static String dbHost;
    public static String dbUser;
    public static String dbPassword;
    public static String appHost;
    public static String appHostUser;
    public static String appHostKeyFile;
    public static String appHostKeyPassword;
    
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
    static {
        loadProperties();
    }

    public static void loadProperties() {
        Properties props = new Properties();
        String propFile = Constants.TESTFILES_PATH + "Test.properties";
        InputStream input = null;
        try {
            input = new FileInputStream(propFile);
            props.load(input);
            webHost = props.getProperty("webHost");
            webUser = props.getProperty("webUser");
            webPassword = props.getProperty("webPassword");
            dbHost = props.getProperty("dbHost");
            dbUser = props.getProperty("dbUser");
            dbPassword = props.getProperty("dbPassword");
            appHost = props.getProperty("appHost");
            appHostUser = props.getProperty("appHostUser");
            appHostKeyFile = props.getProperty("appHostKeyFile");
            appHostKeyPassword = props.getProperty("appHostKeyPassword");
            
            input.close();
            System.out.println(String.format(">> Property file \"%s\" loaded", propFile));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
    public void assertTrueAndContinue(Boolean test) {
        try {
            assertTrue(test);
        } catch (Throwable t) {
            collector.addError(t);
        }
    }
    
    public void assertEqualsAndContinue(Object expected, Object actual) {
        try {
        	assertEquals(expected, actual);
        } catch (Throwable t) {
            collector.addError(t);
        }
    }

}
