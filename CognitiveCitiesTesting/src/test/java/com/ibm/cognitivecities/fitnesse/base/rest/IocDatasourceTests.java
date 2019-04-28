package com.ibm.cognitivecities.fitnesse.base.rest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import resources.Constants;

import com.ibm.cognitivecities.fitnesse.base.TestBase;
import com.ibm.cognitivecities.fitnesse.fixtures.database.DatabaseSetUp;
import com.ibm.cognitivecities.fitnesse.fixtures.database.DatabaseTearDown;
import com.ibm.cognitivecities.fitnesse.fixtures.database.GenericDatabaseFixture;
import com.ibm.cognitivecities.fitnesse.fixtures.rest.ApplicationSetUp;
import com.ibm.cognitivecities.fitnesse.fixtures.rest.GenericServiceValidatorFixture;
import com.ibm.cognitivecities.fitnesse.scenarios.util.FileScenarios;
import com.ibm.cognitivecities.fitnesse.scenarios.util.TimeScenarios;


public class IocDatasourceTests extends TestBase {
	
	@Before
	public void applicationSetUp() {
		ApplicationSetUp.host = TestBase.webHost;
		ApplicationSetUp.urlbase = "/ibm/ioc/api";
		ApplicationSetUp.user = TestBase.webUser;
		ApplicationSetUp.password = TestBase.webPassword;
	}
	
	@Before
	public void databaseSetUp() {
		DatabaseSetUp setup = new DatabaseSetUp();
		setup.setHost(TestBase.dbHost);
		setup.setPort("50000");
		setup.setConnectionName("iocdata");
		setup.setDatabaseName("iocdata");
		setup.setUser(TestBase.dbUser);
		setup.setPassword(TestBase.dbPassword);
		setup.execute();
		
		setup.setHost(TestBase.dbHost);
		setup.setPort("50000");
		setup.setConnectionName("iocdb");
		setup.setDatabaseName("iocdb");
		setup.setUser(TestBase.dbUser);
		setup.setPassword(TestBase.dbPassword);
		setup.execute();
	}
	
	@After
	public void TearDown() {
		new DatabaseTearDown();
	}
	
	@Test
	public void databaseDatasourceLifecycleTest() {
		GenericServiceValidatorFixture gsf =  new GenericServiceValidatorFixture();
		GenericDatabaseFixture gdf = new GenericDatabaseFixture();
		TimeScenarios ts = new TimeScenarios();
		String name = "db_table_test";
		String dsid = null;
		String itemid = null;
		
		// Create source table for data source
		createSourceTable(gdf);
		
		// Create data source using the source table
		gsf.setRequestType("POST");
		gsf.setServiceName("/datasource-service/datasources/");
		gsf.setRequestBody(String.format("{ \"name\":\"%s\", \"lastUpdateDate\":1554313063683, \"messages\":[ { \"messageId\":\"CIYRS0008I\", \"messageText\":\"{\\\"key\\\":\\\"CIYRS0008I\\\",\\\"group\\\":\\\"DataSourceApp\\\"}\", \"i18nMessageText\":\"The data source Database Table Test was saved.\" } ], \"type\":\"Database\", \"scope\":\"DATASOURCE\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_f747fdee-5b74-4534-8786-ae97a3e1a43e\", \"resources\":[ { \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_f747fdee-5b74-4534-8786-ae97a3e1a43e\", \"value\":\"Database Table Test\" } ] }, \"i18nLabel\":\"Database Table Test\", \"description\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_d565a122-1b46-48db-8651-c370f6967137\", \"resources\":[ { \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_d565a122-1b46-48db-8651-c370f6967137\", \"value\":\"\" } ] }, \"receiver\":{ \"type\":\"com.ibm.ioc.datareceiver.types.DataReceiverTable\", \"interval\":1, \"unit\":\"MINUTES\", \"started\":true, \"skippedColumns\":[ ] }, \"source\":{ \"protocol\":\"ioc.datasource.ColumnParserDatabase\", \"options\":\"DB2\", \"host\":\"dubperfwow2-db.mul.ie.ibm.com\", \"port\":50000, \"user\":\"db2i1own\", \"password\":\"us3rpa88\", \"directory\":\"iocdb\", \"filename\":\"ioc.ds_source_table\" }, \"columns\":[ { \"type\":\"INTEGER\", \"sourceType\":\"INTEGER\", \"sourceName\":\"ID\", \"hint\":\"ID\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_a9128b9b-455f-47df-a17c-a3398db4ab30\", \"i18nLabel\":\"ID\", \"resources\":[ { \"id\":83315, \"lastUpdateDate\":1554313063902, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_a9128b9b-455f-47df-a17c-a3398db4ab30\", \"value\":\"ID\" } ] }, \"category\":\"KEY\", \"length\":\"10\", \"scale\":\"0\", \"enumerated\":false, \"isId\":true, \"optimize\":true, \"updatable\":false, \"metric\":false, \"chart\":false, \"hidden\":false, \"ordinal\":0, \"hover\":false, \"previewCard\":true, \"helpMessage\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_65345362-d788-493a-9c5f-c5a2f704b7e1\", \"i18nLabel\":\"\", \"resources\":[ { \"id\":83316, \"lastUpdateDate\":1554313063905, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_65345362-d788-493a-9c5f-c5a2f704b7e1\", \"value\":\"\" } ] }, \"userFilterInput\":false, \"displayedonMap\":false, \"targetNames\":[ \"Property_ID\" ] }, { \"type\":\"TIMESTAMP\", \"sourceType\":\"TIMESTAMP\", \"sourceName\":\"LASTCHANGED\", \"hint\":\"LASTCHANGED\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_e22f189e-0dd6-4553-a853-be2a8400b43c\", \"i18nLabel\":\"LASTCHANGED\", \"resources\":[ { \"id\":83323, \"lastUpdateDate\":1554313063928, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_e22f189e-0dd6-4553-a853-be2a8400b43c\", \"value\":\"LASTCHANGED\" } ] }, \"category\":\"MINIMAL\", \"length\":\"26\", \"scale\":\"6\", \"enumerated\":false, \"isId\":false, \"optimize\":false, \"updatable\":true, \"metric\":false, \"chart\":false, \"hidden\":false, \"ordinal\":0, \"hover\":false, \"previewCard\":true, \"helpMessage\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_2400baef-9fbc-4890-b0eb-6304e55085d2\", \"i18nLabel\":\"\", \"resources\":[ { \"id\":83324, \"lastUpdateDate\":1554313063932, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_2400baef-9fbc-4890-b0eb-6304e55085d2\", \"value\":\"\" } ] }, \"userFilterInput\":false, \"displayedonMap\":false, \"targetNames\":[ \"LAST_UPDATE_DATE_TIME\" ], \"format\":\"TIMESTAMP\" }, { \"type\":\"VARCHAR\", \"sourceType\":\"VARCHAR\", \"sourceName\":\"LOCATION\", \"hint\":\"LOCATION\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_54913dd7-eaed-478b-84d8-19836f1d2296\", \"i18nLabel\":\"LOCATION\", \"resources\":[ { \"id\":83321, \"lastUpdateDate\":1554313063921, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_54913dd7-eaed-478b-84d8-19836f1d2296\", \"value\":\"LOCATION\" } ] }, \"category\":\"MINIMAL\", \"length\":\"500\", \"scale\":\"0\", \"enumerated\":false, \"isId\":false, \"optimize\":false, \"updatable\":true, \"metric\":false, \"chart\":false, \"hidden\":false, \"ordinal\":0, \"hover\":false, \"previewCard\":true, \"helpMessage\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_76d7a203-16af-413f-873b-ac9cca8ad5bf\", \"i18nLabel\":\"\", \"resources\":[ { \"id\":83322, \"lastUpdateDate\":1554313063925, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_76d7a203-16af-413f-873b-ac9cca8ad5bf\", \"value\":\"\" } ] }, \"userFilterInput\":false, \"displayedonMap\":false, \"targetNames\":[ \"LOCATION\" ], \"format\":\"SHAPE\", \"lookup\":{ \"directory\":\"\", \"filename\":\"\" } }, { \"type\":\"VARCHAR\", \"sourceType\":\"VARCHAR\", \"sourceName\":\"NAME\", \"hint\":\"NAME\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_414cf357-3c77-4d70-b60c-9d757c939a1b\", \"i18nLabel\":\"NAME\", \"resources\":[ { \"id\":83317, \"lastUpdateDate\":1554313063909, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_414cf357-3c77-4d70-b60c-9d757c939a1b\", \"value\":\"NAME\" } ] }, \"category\":\"MINIMAL\", \"length\":\"128\", \"scale\":\"0\", \"enumerated\":false, \"isId\":false, \"optimize\":false, \"updatable\":true, \"metric\":false, \"chart\":false, \"hidden\":false, \"ordinal\":0, \"hover\":false, \"previewCard\":true, \"helpMessage\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_d400529c-b6e0-413f-8cf4-e4468d62f8ba\", \"i18nLabel\":\"\", \"resources\":[ { \"id\":83318, \"lastUpdateDate\":1554313063912, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_d400529c-b6e0-413f-8cf4-e4468d62f8ba\", \"value\":\"\" } ] }, \"userFilterInput\":false, \"displayedonMap\":false, \"targetNames\":[ \"NAME\" ], \"format\":\"STRING\" }, { \"type\":\"TIMESTAMP\", \"sourceType\":\"TIMESTAMP\", \"sourceName\":\"STARTDATETIME\", \"hint\":\"STARTDATETIME\", \"label\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_94f02c05-ab39-4d14-a10e-d30f02b2f888\", \"i18nLabel\":\"STARTDATETIME\", \"resources\":[ { \"id\":83319, \"lastUpdateDate\":1554313063915, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_94f02c05-ab39-4d14-a10e-d30f02b2f888\", \"value\":\"STARTDATETIME\" } ] }, \"category\":\"MINIMAL\", \"length\":\"26\", \"scale\":\"6\", \"enumerated\":false, \"isId\":false, \"optimize\":false, \"updatable\":true, \"metric\":false, \"chart\":false, \"hidden\":false, \"ordinal\":0, \"hover\":false, \"previewCard\":true, \"helpMessage\":{ \"group\":\"DataSourceI18n\", \"key\":\"i18n_a58fbc7f-dee7-4adc-807d-1339553775b9\", \"i18nLabel\":\"\", \"resources\":[ { \"id\":83320, \"lastUpdateDate\":1554313063918, \"group\":\"DataSourceI18n\", \"locale\":\"en\", \"key\":\"i18n_a58fbc7f-dee7-4adc-807d-1339553775b9\", \"value\":\"\" } ] }, \"userFilterInput\":false, \"displayedonMap\":false, \"targetNames\":[ \"START_DATE_TIME\" ], \"format\":\"TIMESTAMP\" } ], \"geometry\":\"POINT\", \"archive\":0, \"boundary\":false, \"boundaryFilterPane\":false, \"correlation\":false, \"report\":false, \"serverRendering\":false, \"nonTrustedSource\":false, \"comments\":false, \"voting\":false, \"imageUploads\":false, \"icon\":\"311.png\", \"radius\":0, \"sides\":0, \"styles\":[ { \"name\":\"_base_\", \"type\":\"COLOR\", \"line\":\"#000000\", \"value\":\"#d3d3d3\", \"weight\":1, \"opacity\":1, \"scope\":\"DATASOURCE\" } ], \"extensions\":[ { \"name\":\"category\", \"label\":{ }, \"type\":\"CUSTOM\", \"disabled\":false, \"multiple\":false, \"module\":\"ICPO\", \"associated\":\"1\" }, { \"name\":\"dimension\", \"label\":{ }, \"type\":\"CUSTOM\", \"disabled\":false, \"multiple\":false, \"module\":\"ICPO\", \"associated\":\"false\" }, { \"name\":\"name\", \"label\":{ }, \"type\":\"CUSTOM\", \"disabled\":false, \"multiple\":false, \"module\":\"ICPO\", \"associated\":\"db_table_test\" }, { \"name\":\"resource\", \"label\":{ }, \"type\":\"CUSTOM\", \"disabled\":false, \"multiple\":false, \"module\":\"ICPO\", \"associated\":\"false\" } ], \"customProperties\":{ }, \"optionalDateTime\":false, \"minZoomForLoad\":0, \"dateFormat\":\"\", \"timeFormat\":\"\", \"areas\":\"\", \"routingLogic\":\"AND\", \"rules\":[ ]}",name));
		gsf.execute();
		assertTrue(gsf.getResponseCode()==200);
		dsid = gsf.getResponseId();
		gsf.reset();
		
		// Verify the data source was created 
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/datasource-service/datasources/%s",dsid));
		gsf.execute();
		assertTrue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains(String.format("\"name\":\"%s\"",name)));
		gsf.reset();
		
		ts.pauseExecution(5000);
		
		// Verify the target table has two records
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_db_table_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":2}]");
		
		// Use the data injection service to add a new item 
		gsf.setRequestType("POST");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems",dsid));
		gsf.setRequestBody("{\"Property_ID\":3,\"Name\":\"NAME3\",\"StartDateTime\":\"2019-04-04T11:00:00-00:00\",\"EndDateTime\":\"\",\"Location\":\"POINT (-97.72190603 30.56611663)\",\"DELETEFLAG\":false }");
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		itemid = gsf.getResponseId();
		gsf.reset();
		
		// Verify the item was added
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3\""));
		gsf.reset();

		// Update the item
		gsf.setRequestType("PUT");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems/%s",dsid,itemid));
		gsf.setRequestBody("{\"Property_ID\":3,\"Name\":\"NAME3 (Updated)\",\"StartDateTime\":\"2019-04-04T11:00:00-00:00\",\"EndDateTime\":\"\",\"Location\":\"POINT (-97.72190603 30.56611663)\",\"DELETEFLAG\":false}");
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		gsf.reset();
		
		// Verify the item was updated
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3 (updated)\""));
		gsf.reset();
		
		// Verify the item is returned with valid date filter
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3&criterion=startDateTime%%3E%%3D2019-04-04T10%%3A00%%3A00-00%%3A00",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3 (updated)\""));
		gsf.reset();
		
		// Verify the item is not returned with out-of-range date filter
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3&criterion=startDateTime%%3C%%3D2019-03-30T00%%3A00%%3A00-00%%3A00",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"features\":[]"));
		gsf.reset();
		
		// Delete the item
		gsf.setRequestType("DELETE");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems/%s",dsid,itemid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==204);
		gsf.reset();
		
		// Verify the new item is marked as deleted
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"deleted\":true"));
		gsf.reset();
		
		// Verify we still have 3 items (IOC doesn't delete the item)
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_db_table_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":3}]");
		
		// Add a record to the source with ID=1. The record will update the existing ID=1 (ID column is 'used as id' column)
		gdf.setConnectionName("iocdb");
		gdf.setStatementType("create");
		gdf.setStatementText("insert into ioc.ds_source_table values (1,'NAME1 (Updated)','2019-04-04 12:00:00.0','POINT(-97.69575 30.590381)',CURRENT TIMESTAMP)");
		gdf.execute();
		
		ts.pauseExecution(115000);
		
		// Verify the target table was updated rather than inserted (still 3 items)
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_db_table_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":3}]");
		
		// Verify the name property was updated for ID=1
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D1",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name1 (updated)\""));
		gsf.reset();
		
		// Delete the data source
		gsf.setRequestType("DELETE");
		gsf.setServiceName(String.format("/datasource-service/datasources/%s",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==204);
		gsf.reset();
		
		deleteSourceTable(gdf);
	}
	
	private void createSourceTable(GenericDatabaseFixture gdf) {
		gdf.setConnectionName("iocdb");
		gdf.setStatementText("CREATE TABLE ioc.ds_source_table (ID INTEGER NOT NULL, NAME VARCHAR(128) NOT NULL, STARTDATETIME TIMESTAMP, LOCATION VARCHAR(500), LASTCHANGED TIMESTAMP) ORGANIZE BY ROW DATA CAPTURE NONE IN USERSPACE1");
		gdf.setStatementType("create");
		gdf.execute();
		gdf.setStatementText("insert into ioc.ds_source_table values (1,'NAME1','2019-04-01 12:00:00.0','POINT(-97.69575 30.590381)','2019-04-03 12:00:00.0')");
		gdf.execute();
		gdf.setStatementText("insert into ioc.ds_source_table values (2,'NAME2','2019-04-02 13:00:00.0','POINT(-97.69675 30.590481)','2019-04-03 13:00:00.0')");
		gdf.execute();
		gdf.setStatementText("select count as count from ioc.ds_source_table");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":2}]");
	}
	
	private void deleteSourceTable(GenericDatabaseFixture gdf) {
		gdf.setConnectionName("iocdb");
		gdf.setStatementText("drop table ioc.ds_source_table");
		gdf.setStatementType("create");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
	}
	
	@Test
	public void csvDatasourceLifecycleTest() {
		GenericServiceValidatorFixture gsf =  new GenericServiceValidatorFixture();
		GenericDatabaseFixture gdf = new GenericDatabaseFixture();
		TimeScenarios ts = new TimeScenarios();
		FileScenarios fileScenarios = new FileScenarios();
		String name = "csv_test";
		String dsid = null;
		String itemid = null;
		
		fileScenarios.copyFileRemote(Constants.TESTFILES_PATH + "datasource_init.csv", "/opt/IBM/ioc/csv/names.csv", TestBase.appHost, TestBase.appHostUser, Constants.TESTFILES_PATH + TestBase.appHostKeyFile, TestBase.appHostKeyPassword);
		
		// Create data source using the source table
		gsf.setRequestType("POST");
		gsf.setServiceName("/datasource-service/datasources/");
		gsf.setRequestBody(String.format("{\"name\":\"%s\",\"lastUpdateDate\":1554453178886,\"messages\":[{\"messageId\":\"CIYRS0008I\",\"messageText\":\"{\\\"key\\\":\\\"CIYRS0008I\\\",\\\"group\\\":\\\"DataSourceApp\\\"}\",\"i18nMessageText\":\"The data source CSV Test was saved.\"}],\"type\":\"CSV\",\"scope\":\"DATASOURCE\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"i18n_6aaa21cf-57f4-4c65-8c3b-53e2b14ccc55\",\"resources\":[{\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"i18n_6aaa21cf-57f4-4c65-8c3b-53e2b14ccc55\",\"value\":\"CSV Test\"}]},\"i18nLabel\":\"CSV Test\",\"description\":{\"group\":\"DataSourceI18n\",\"key\":\"i18n_5ba0ce4d-0897-41c4-8350-c1c09d72f64d\",\"resources\":[{\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"i18n_5ba0ce4d-0897-41c4-8350-c1c09d72f64d\",\"value\":\"\"}]},\"receiver\":{\"type\":\"com.ibm.ioc.datareceiver.types.DataReceiverCSV\",\"interval\":1,\"unit\":\"MINUTES\",\"started\":false,\"skippedColumns\":[]},\"source\":{\"protocol\":\"ioc.datasource.ColumnParserCsv\",\"directory\":\"/opt/IBM/ioc/csv/\",\"filename\":\"names.csv\"},\"columns\":[{\"type\":\"INTEGER\",\"sourceType\":\"VARCHAR\",\"sourceName\":\"0\",\"hint\":\"ID\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"8601acf4-c867-46a8-b5f9-f19ca5ca36fe\",\"i18nLabel\":\"ID\",\"resources\":[{\"id\":83844,\"lastUpdateDate\":1554453179461,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"8601acf4-c867-46a8-b5f9-f19ca5ca36fe\",\"value\":\"ID\"}]},\"category\":\"KEY\",\"enumerated\":false,\"isId\":true,\"optimize\":true,\"updatable\":false,\"metric\":false,\"chart\":false,\"hidden\":false,\"ordinal\":0,\"hover\":false,\"previewCard\":true,\"helpMessage\":{\"group\":\"DataSourceI18n\",\"key\":\"7edb532a-02e8-468a-850b-93ef682b6368\",\"i18nLabel\":\"\",\"resources\":[{\"id\":83845,\"lastUpdateDate\":1554453179479,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"7edb532a-02e8-468a-850b-93ef682b6368\",\"value\":\"\"}]},\"userFilterInput\":false,\"displayedonMap\":false,\"targetNames\":[\"Property_ID\"]},{\"type\":\"TIMESTAMP\",\"sourceType\":\"VARCHAR\",\"sourceName\":\"4\",\"hint\":\"LASTCHANGED\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"fed4e75c-b9ba-484d-bb68-16b0209d8b7c\",\"i18nLabel\":\"LASTCHANGED\",\"resources\":[{\"id\":83852,\"lastUpdateDate\":1554453179518,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"fed4e75c-b9ba-484d-bb68-16b0209d8b7c\",\"value\":\"LASTCHANGED\"}]},\"category\":\"MINIMAL\",\"enumerated\":false,\"isId\":false,\"optimize\":false,\"updatable\":true,\"metric\":false,\"chart\":false,\"hidden\":false,\"ordinal\":0,\"hover\":false,\"previewCard\":true,\"helpMessage\":{\"group\":\"DataSourceI18n\",\"key\":\"9b0aa82e-b255-4597-a02d-718e81178b22\",\"i18nLabel\":\"\",\"resources\":[{\"id\":83853,\"lastUpdateDate\":1554453179532,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"9b0aa82e-b255-4597-a02d-718e81178b22\",\"value\":\"\"}]},\"userFilterInput\":false,\"displayedonMap\":false,\"targetNames\":[\"LAST_UPDATE_DATE_TIME\"],\"format\":\"TIMESTAMP\"},{\"type\":\"VARCHAR\",\"sourceType\":\"VARCHAR\",\"sourceName\":\"3\",\"hint\":\"LOCATION\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"02dd0c55-3dbf-4750-a80a-0883ee42a664\",\"i18nLabel\":\"LOCATION\",\"resources\":[{\"id\":83850,\"lastUpdateDate\":1554453179507,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"02dd0c55-3dbf-4750-a80a-0883ee42a664\",\"value\":\"LOCATION\"}]},\"category\":\"MINIMAL\",\"enumerated\":false,\"isId\":false,\"optimize\":false,\"updatable\":true,\"metric\":false,\"chart\":false,\"hidden\":false,\"ordinal\":0,\"hover\":false,\"previewCard\":true,\"helpMessage\":{\"group\":\"DataSourceI18n\",\"key\":\"743d7523-f3df-45e3-a993-2200a109a830\",\"i18nLabel\":\"\",\"resources\":[{\"id\":83851,\"lastUpdateDate\":1554453179513,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"743d7523-f3df-45e3-a993-2200a109a830\",\"value\":\"\"}]},\"userFilterInput\":false,\"displayedonMap\":false,\"targetNames\":[\"LOCATION\"],\"format\":\"SHAPE\",\"lookup\":{\"directory\":\"\",\"filename\":\"\"}},{\"type\":\"VARCHAR\",\"sourceType\":\"VARCHAR\",\"sourceName\":\"1\",\"hint\":\"NAME\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"16cdcd63-4759-4402-bfe7-61195acd51e4\",\"i18nLabel\":\"NAME\",\"resources\":[{\"id\":83846,\"lastUpdateDate\":1554453179487,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"16cdcd63-4759-4402-bfe7-61195acd51e4\",\"value\":\"NAME\"}]},\"category\":\"MINIMAL\",\"enumerated\":false,\"isId\":false,\"optimize\":false,\"updatable\":true,\"metric\":false,\"chart\":false,\"hidden\":false,\"ordinal\":0,\"hover\":false,\"previewCard\":true,\"helpMessage\":{\"group\":\"DataSourceI18n\",\"key\":\"0da1a12b-8418-4895-9af3-f93cb9d1dd77\",\"i18nLabel\":\"\",\"resources\":[{\"id\":83847,\"lastUpdateDate\":1554453179492,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"0da1a12b-8418-4895-9af3-f93cb9d1dd77\",\"value\":\"\"}]},\"userFilterInput\":false,\"displayedonMap\":false,\"targetNames\":[\"NAME\"],\"format\":\"STRING\"},{\"type\":\"TIMESTAMP\",\"sourceType\":\"VARCHAR\",\"sourceName\":\"2\",\"hint\":\"STARTDATETIME\",\"label\":{\"group\":\"DataSourceI18n\",\"key\":\"9dcb1dc9-2a4e-4397-8935-e96558463af3\",\"i18nLabel\":\"STARTDATETIME\",\"resources\":[{\"id\":83848,\"lastUpdateDate\":1554453179497,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"9dcb1dc9-2a4e-4397-8935-e96558463af3\",\"value\":\"STARTDATETIME\"}]},\"category\":\"MINIMAL\",\"enumerated\":false,\"isId\":false,\"optimize\":false,\"updatable\":true,\"metric\":false,\"chart\":false,\"hidden\":false,\"ordinal\":0,\"hover\":false,\"previewCard\":true,\"helpMessage\":{\"group\":\"DataSourceI18n\",\"key\":\"35f33e3d-2c69-44b6-b6c6-9962a5dc2a9d\",\"i18nLabel\":\"\",\"resources\":[{\"id\":83849,\"lastUpdateDate\":1554453179502,\"group\":\"DataSourceI18n\",\"locale\":\"en\",\"key\":\"35f33e3d-2c69-44b6-b6c6-9962a5dc2a9d\",\"value\":\"\"}]},\"userFilterInput\":false,\"displayedonMap\":false,\"targetNames\":[\"START_DATE_TIME\"],\"format\":\"TIMESTAMP\"}],\"geometry\":\"POINT\",\"archive\":0,\"boundary\":false,\"boundaryFilterPane\":false,\"correlation\":false,\"report\":false,\"serverRendering\":false,\"nonTrustedSource\":false,\"comments\":false,\"voting\":false,\"imageUploads\":false,\"icon\":\"311.png\",\"radius\":0,\"sides\":0,\"styles\":[{\"name\":\"_base_\",\"type\":\"COLOR\",\"line\":\"#000000\",\"value\":\"#d3d3d3\",\"weight\":1,\"opacity\":1,\"scope\":\"DATASOURCE\"}],\"extensions\":[{\"name\":\"category\",\"label\":{},\"type\":\"CUSTOM\",\"disabled\":false,\"multiple\":false,\"module\":\"ICPO\",\"associated\":\"1\"},{\"name\":\"dimension\",\"label\":{},\"type\":\"CUSTOM\",\"disabled\":false,\"multiple\":false,\"module\":\"ICPO\",\"associated\":\"false\"},{\"name\":\"name\",\"label\":{},\"type\":\"CUSTOM\",\"disabled\":false,\"multiple\":false,\"module\":\"ICPO\",\"associated\":\"csv_test\"},{\"name\":\"resource\",\"label\":{},\"type\":\"CUSTOM\",\"disabled\":false,\"multiple\":false,\"module\":\"ICPO\",\"associated\":\"false\"}],\"customProperties\":{},\"optionalDateTime\":false,\"minZoomForLoad\":0,\"dateFormat\":\"\",\"timeFormat\":\"\",\"areas\":\"\",\"routingLogic\":\"AND\",\"rules\":[]}",name));
		gsf.execute();
		assertTrue(gsf.getResponseCode()==200);
		dsid = gsf.getResponseId();
		gsf.reset();
		
		// Verify the data source was created 
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/datasource-service/datasources/%s",dsid));
		gsf.execute();
		assertTrue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains(String.format("\"name\":\"%s\"",name)));
		gsf.reset();
		
		ts.pauseExecution(3000);
		
		// Verify the target table has two records
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_csv_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":2}]");
		
		// Use the data injection service to add a new item 
		gsf.setRequestType("POST");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems",dsid));
		gsf.setRequestBody("{\"Property_ID\":3,\"Name\":\"NAME3\",\"StartDateTime\":\"2019-04-04T11:00:00-00:00\",\"EndDateTime\":\"\",\"Location\":\"POINT (-97.72190603 30.56611663)\",\"DELETEFLAG\":false }");
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		itemid = gsf.getResponseId();
		gsf.reset();
		
		// Verify the item was added
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3\""));
		gsf.reset();

		// Update the item
		gsf.setRequestType("PUT");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems/%s",dsid,itemid));
		gsf.setRequestBody("{\"Property_ID\":3,\"Name\":\"NAME3 (Updated)\",\"StartDateTime\":\"2019-04-04T11:00:00-00:00\",\"EndDateTime\":\"\",\"Location\":\"POINT (-97.72190603 30.56611663)\",\"DELETEFLAG\":false}");
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		gsf.reset();
		
		// Verify the item was updated
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3 (updated)\""));
		gsf.reset();
		
		// Verify the item is returned with valid date filter
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3&criterion=startDateTime%%3E%%3D2019-04-04T10%%3A00%%3A00-00%%3A00",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name3 (updated)\""));
		gsf.reset();
		
		// Verify the item is not returned with out-of-range date filter
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3&criterion=startDateTime%%3C%%3D2019-03-30T00%%3A00%%3A00-00%%3A00",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"features\":[]"));
		gsf.reset();
		
		// Delete the item
		gsf.setRequestType("DELETE");
		gsf.setServiceName(String.format("/data-injection-service/datablocks/%s/dataitems/%s",dsid,itemid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==204);
		gsf.reset();
		
		// Verify the new item is marked as deleted
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D3",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrue(gsf.getResponseMessage().toLowerCase().contains("\"deleted\":true"));
		gsf.reset();
		
		// Verify we still have 3 items (IOC doesn't delete the item)
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_csv_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":3}]");
		
		// Copy CSV with update to record number 1. Note that the ID column is a 'used as id' column in this datasource
		fileScenarios.copyFileRemote(Constants.TESTFILES_PATH + "datasource_delta.csv", "/opt/IBM/ioc/csv/names.csv", TestBase.appHost, TestBase.appHostUser, Constants.TESTFILES_PATH + TestBase.appHostKeyFile, TestBase.appHostKeyPassword);
		
		ts.pauseExecution(115000);
		
		// Verify the target table was updated rather than inserted (still 3 items)
		gdf.setConnectionName("iocdata");
		gdf.setStatementText("select count as count from ioc.target_table_csv_test");
		gdf.setStatementType("read");
		gdf.execute();
		assertEqualsAndContinue(gdf.getResponseMessage(),"OK");
		assertEqualsAndContinue(gdf.getResponse(),"[{\"COUNT\":3}]");
		
		// Verify the name property was updated for ID=1
		gsf.setRequestType("GET");
		gsf.setServiceName(String.format("/spatial-service/features/%s?criterion=Property_ID%%3D1",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==200);
		assertTrueAndContinue(gsf.getResponseMessage().toLowerCase().contains("\"name\":\"name1 (updated from csv)\""));
		gsf.reset();
		
		// Delete the data source
		gsf.setRequestType("DELETE");
		gsf.setServiceName(String.format("/datasource-service/datasources/%s",dsid));
		gsf.execute();
		assertTrueAndContinue(gsf.getResponseCode()==204);
		gsf.reset();		
	}
}
