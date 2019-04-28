package com.ibm.cognitivecities.fitnesse.utility;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import resources.Constants;

import com.ibm.cognitivecities.fitnesse.base.TestBase;
import com.ibm.cognitivecities.fitnesse.scenarios.util.FileScenarios;



public class FileUtilTests extends TestBase {
	@Before
	public void applicationSetUp() {
	}
	
	@After
	public void TearDown() {
	}
	
	@Test
	public void localFileCopySuccessTest() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFile(Constants.TESTFILES_PATH + "datasource_init.csv", Constants.TESTFILES_PATH + "datasource_init_copied.csv");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
	}

	@Test
	public void localFileCopyFail_FileNotFound_Test() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFile(Constants.TESTFILES_PATH + "datasource_ini.csv", Constants.TESTFILES_PATH + "datasource_init_copied.csv");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("KO"));
		System.out.println(scenario.fileUtilResponse());
	}

	@Test
	public void remoteFileCopySuccessTest() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFileRemote(Constants.TESTFILES_PATH + "datasource_init.csv",
								"/opt/IBM/ioc/csv/names.csv",
								TestBase.appHost, "root", Constants.TESTFILES_PATH + "windows_laptop_brendan.ppk", null);
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
	}

	@Test
	public void remoteFileCopyFail_KeyPathError_Test() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFileRemote(Constants.TESTFILES_PATH + "datasource_init.csv", 
								"/opt/IBM/ioc/csv/names.csv",
								TestBase.appHost, "root", Constants.TESTFILES_PATH + "windows_laptop_brenda.ppk", null);
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("KO"));
		System.out.println(scenario.fileUtilResponse());
	}
	
	@Test
	public void remoteFileCopyFail_FileNotFound_Test() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFileRemote(Constants.TESTFILES_PATH + "datasource_ini.csv", 
								"/opt/IBM/ioc/csv/datasource_init.csv",
								TestBase.appHost, "root", Constants.TESTFILES_PATH + "windows_laptop_brendan.ppk", null);
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("KO"));
		System.out.println(scenario.fileUtilResponse());
	}

	@Test
	public void remoteFileCopyFail_InvalidHost_Test() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFileRemote(Constants.TESTFILES_PATH + "datasource_init.csv",
								"/opt/IBM/ioc/csv/names.csv",
								TestBase.appHost+"xxx", "root", Constants.TESTFILES_PATH + "windows_laptop_brendan.ppk", null);
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("KO"));
	}

	@Test
	public void replaceStringsInFileTest() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFile(Constants.TESTFILES_PATH + "template1.txt", Constants.TESTFILES_PATH + "test.txt");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
		scenario.replaceStringsInFile(Constants.TESTFILES_PATH + "test.txt", "from1:to1;from2:to2;from3:to3;from4:to4", ":", ";");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
	}
	
	@Test
	public void deleteFileSuccessTest() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFile(Constants.TESTFILES_PATH + "template1.txt", Constants.TESTFILES_PATH + "deleteme.txt");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
		scenario.deleteFile(Constants.TESTFILES_PATH + "deleteme.txt");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
	}
	
	@Test
	public void deleteFileFail_FileNotFound_Test() {
		FileScenarios scenario = new FileScenarios();
		scenario.copyFile(Constants.TESTFILES_PATH + "template1.txt", Constants.TESTFILES_PATH + "deleteme.txt");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("OK"));
		scenario.deleteFile(Constants.TESTFILES_PATH + "deletemex.txt");
		assertTrue(scenario.fileUtilResponseMessage().contentEquals("KO"));
	}
}
