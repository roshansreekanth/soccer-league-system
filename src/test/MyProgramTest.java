package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.DBOperations;
import models.Manager;

public class MyProgramTest 
{
	private Manager managerObject;
	private List managersList;

	@Before
	public void setUp()
	{
		managerObject = new Manager("John Doe", "12345", "testmanager@gmail.com", LocalDate.of(1980, 05, 05), 6.0f);
		managerObject.setStatus(false);
		managerObject.setTeam(null);
	}
	
	@Test
	public void testAddManager() throws DerbySQLIntegrityConstraintViolationException
	{
		DBOperations.create(managerObject);
		managersList = DBOperations.getAll("Manager");
		System.out.println(managersList.size() > 0);
		assertTrue(managersList.size() > 0);
	}
	
	@Test
	public void testRemoveManager()
	{
		DBOperations.remove(managerObject);
		List managersList = DBOperations.getAll("Manager");
		assertTrue(managersList.size() == 0);
	}
}
