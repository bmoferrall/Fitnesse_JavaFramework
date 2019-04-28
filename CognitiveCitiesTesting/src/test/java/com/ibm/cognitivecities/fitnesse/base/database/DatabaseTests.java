package com.ibm.cognitivecities.fitnesse.base.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ibm.cognitivecities.fitnesse.base.TestBase;
import com.ibm.cognitivecities.fitnesse.fixtures.database.DatabaseSetUp;
import com.ibm.cognitivecities.fitnesse.fixtures.database.DatabaseTearDown;
import com.ibm.cognitivecities.fitnesse.fixtures.database.GenericDatabaseFixture;
import com.ibm.cognitivecities.fitnesse.scenarios.database.DatabaseScenarios;




public class DatabaseTests extends TestBase {
	DatabaseSetUp setup;
	
	@Before
	public void SetUp() {
		setup = new DatabaseSetUp();
		setup.setHost(TestBase.dbHost);
		setup.setPort("50000");
		setup.setConnectionName("wih");
		setup.setDatabaseName("wihdb");
		setup.setUser(TestBase.dbUser);
		setup.setPassword(TestBase.dbPassword);
		setup.execute();
	}
	
	@After
	public void TearDown() {
		new DatabaseTearDown();
	}
	
	@Test
	public void verifyDatabaseConnection() {
		assertEquals(DatabaseSetUp.connected,true);
		assertEquals(DatabaseSetUp.connections.size(),1);
	}
	
	@Test
	public void queryFixtureTest() {
		assertEquals(DatabaseSetUp.connected,true);
		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("wih");
		gbf.setResponseFormat("json");
		gbf.setStatementText("select count from wih.reading");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.resultCount,1);
	}

	@Test
	public void invalidResponseFormat() {
		assertEquals(DatabaseSetUp.connected,true);
		
		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("wih");
		gbf.setResponseFormat("junk");
		gbf.setStatementText("select count from wih.reading");
		gbf.setStatementType("read");
		gbf.execute();
		assertTrue(gbf.responseMessage.contains("Unsupported response format"));
	}

	@Test
	public void invalidStatementType() {
		assertEquals(DatabaseSetUp.connected,true);
		
		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("wih");
		gbf.setResponseFormat("json");
		gbf.setStatementText("select count from wih.reading");
		gbf.setStatementType("junk");
		gbf.execute();
		assertTrue(gbf.responseMessage.contains("Unsupported statement type"));
	}
	
	@Test
	public void invalidConnectionName() {
		assertEquals(DatabaseSetUp.connected,true);
		
		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("xxx");
		gbf.setResponseFormat("json");
		gbf.setStatementText("select count from wih.reading");
		gbf.setStatementType("read");
		gbf.execute();
		assertTrue(gbf.responseMessage.contains("No active connection found for connection name"));
	}
	
	@Test
	public void createDuplicateDatabaseConnection() {
		assertEquals(DatabaseSetUp.connected,true);
		
		setup.setHost(TestBase.dbHost);
		setup.setPort("50000");
		setup.setConnectionName("wih");
		setup.setDatabaseName("wihdb");
		setup.setUser(TestBase.dbUser);
		setup.setPassword(TestBase.dbPassword);
		setup.execute();
		
		assertEquals(DatabaseSetUp.connections.size(),1);
	}
	
	@Test
	public void deleteFixtureTest() {
		assertEquals(DatabaseSetUp.connected,true);
		
		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("wih");
		gbf.setStatementText("delete from wih.reading where asset_id = -1");
		gbf.setStatementType("delete");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
	}
	
	@Test
	public void crudFixtureTest() {
		assertEquals(DatabaseSetUp.connected,true);

		GenericDatabaseFixture gbf = new GenericDatabaseFixture();
		gbf.setConnectionName("wih");
		
		gbf.setStatementText("CREATE TABLE wih.test_table (ID INTEGER NOT NULL, NAME VARCHAR(100)) ORGANIZE BY ROW DATA CAPTURE NONE IN USERSPACE1");
		gbf.setStatementType("create");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		
		gbf.setStatementText("select count as count from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[{\"COUNT\":0}]");
		assertEquals(gbf.getFirstResultField(),"0");
		assertEquals(gbf.resultCount,1);
		
		gbf.setStatementText("insert into wih.test_table values (1,'Brendan')");
		gbf.setStatementType("create");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		
		gbf.setStatementText("select count as count from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[{\"COUNT\":1}]");
		assertEquals(gbf.getFirstResultField(),"1");
		assertEquals(gbf.resultCount,1);
		
		gbf.setStatementText("select * from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[{\"ID\":1,\"NAME\":\"Brendan\"}]");
		assertEquals(gbf.getFirstResultField(),"1");
		assertEquals(gbf.resultCount,1);
		
		gbf.setStatementText("update wih.test_table set name = 'John' where id = 1");
		gbf.setStatementType("update");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		
		gbf.setStatementText("select * from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[{\"ID\":1,\"NAME\":\"John\"}]");
		assertEquals(gbf.getFirstResultField(),"1");
		assertEquals(gbf.resultCount,1);
		
		gbf.setStatementText("delete from wih.test_table");
		gbf.setStatementType("delete");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		
		gbf.setStatementText("select count as count from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[{\"COUNT\":0}]");
		assertEquals(gbf.getFirstResultField(),"0");
		assertEquals(gbf.resultCount,1);
		
		gbf.setStatementText("select * from wih.test_table");
		gbf.setStatementType("read");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
		assertEquals(gbf.response,"[]");
		assertEquals(gbf.resultCount,0);
		
		gbf.setStatementText("DROP TABLE wih.test_table");
		gbf.setStatementType("create");
		gbf.execute();
		assertEquals(gbf.responseMessage,"OK");
	}
	
	@Test
	public void crudScenarioTest() {
		assertEquals(DatabaseSetUp.connected,true);

		DatabaseScenarios scenario = new DatabaseScenarios();
		
		scenario.executeCreate("wih", "CREATE TABLE wih.test_table (ID INTEGER NOT NULL, NAME VARCHAR(100)) ORGANIZE BY ROW DATA CAPTURE NONE IN USERSPACE1");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		
		scenario.executeRead("wih", "json", "select count as count from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"COUNT\":0}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"0");
		
		scenario.executeCreate("wih", "insert into wih.test_table values (1,'Brendan')");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		
		scenario.executeRead("wih", "json", "select count as count from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"COUNT\":1}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"1");
		
		scenario.executeRead("wih", "json", "select * from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"ID\":1,\"NAME\":\"Brendan\"}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"1");
		
		scenario.executeUpdate("wih","update wih.test_table set name = 'John' where id = 1");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		
		scenario.executeRead("wih", "json", "select count as count from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"COUNT\":1}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"1");
		
		scenario.executeRead("wih", "json", "select * from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"ID\":1,\"NAME\":\"John\"}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"1");
		
		scenario.executeDelete("wih","delete from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		
		scenario.executeRead("wih", "json", "select count as count from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[{\"COUNT\":0}]"));
		assertEquals(scenario.databaseResultCount(),1);
		assertEquals(scenario.databaseFirstResultField(),"0");
		
		scenario.executeRead("wih", "json", "select * from wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
		assertTrue(scenario.databaseResponse().contains("[]"));
		assertEquals(scenario.databaseResultCount(),0);
		
		scenario.executeCreate("wih", "DROP TABLE wih.test_table");
		assertEquals(scenario.databaseResponseMessage(),"OK");
	}

}
