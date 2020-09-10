package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import models.Manager;
import models.Player;
import models.Team;

public abstract class DBOperations 
{
	/**
	 * Persists an entity into the Derby database
	 * @param userObject The object to be persisted
	 * @throws DerbySQLIntegrityConstraintViolationException In case the object to be found already exists
	 */
	public static void create(Object userObject) throws DerbySQLIntegrityConstraintViolationException
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		entitymanager.persist(userObject);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
	
	/**
	 * Removes persistence of an object from the database
	 * @param userObject The object to be removed
	 */
	public static void remove(Object userObject)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		userObject =  entitymanager.merge(userObject);
		entitymanager.remove(userObject);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
	
	/**
	 * Gets all entities persisted by the database
	 * @param tableName The table from which the data is to be fetched from
	 * @return Returns a list containing all the elements from the table
	 */
	public static List getAll(String tableName)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		String queryString = "SELECT x FROM " + tableName + " x";
		List resultList = entitymanager.createQuery(queryString).getResultList();
		entitymanager.close();
		emfactory.close();
		return resultList;
	}
	
	/**
	 * Gets a list of objects from a database based on a conditional value
	 * @param tableName The name of the table from which the data is fetched
	 * @param conditionalColumn The column to be looked at while going through the database
	 * @param conditionalValue The value of the conditional column
	 * @return Returns a list containing all the elements from the table
	 */
	public static List getConditional(String tableName, String conditionalColumn, String conditionalValue)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		String queryString = "SELECT x FROM " + tableName + " x WHERE x." + conditionalColumn + " = " + "'" + conditionalValue + "'" ;
		List resultList = entitymanager.createQuery(queryString).getResultList();
		entitymanager.close();
		emfactory.close();
		return resultList;
	}

	/**
	 * Removes the managers ties to the team
	 * @param managerEmail The email of the manager to be removed
	 * @param teamName The name of the team the manager is removed from
	 */
	public static void updateManagerEntity(String managerEmail, String teamName)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Manager managerEntity = entitymanager.find(Manager.class, managerEmail);
		if(managerEntity != null)
		{
			managerEntity.setTeam(teamName);
			managerEntity.setStatus(true);
			if(teamName == null)
			{
				managerEntity.setStatus(false);
			}
		}
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		System.out.println("FINAL MANAGER " + managerEntity);
	}
	
	/**
	 * Assigns a team to a league
	 * @param teamName The name of the team assigned to the league
	 * @param leagueName The name of the league the team is assigned to
	 */
	public static void updateTeamLeagueEntity(String teamName, String leagueName)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Team teamEntity = entitymanager.find(Team.class, teamName);
		if(teamEntity != null)
		{
			teamEntity.setLeague(leagueName);
			teamEntity.setStatus(true);
			if(leagueName == null)
			{
				teamEntity.setStatus(false);
			}
		}
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
	
	/**
	 * Removes a player from the team when a player is removed
	 * @param playerEmail The email of the player that is to be removed
	 */
	public static void removePlayersTeamEntity(String playerEmail)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Player playerEntity = entitymanager.find(Player.class, playerEmail);
		System.out.println("before player " + playerEntity);
		if(playerEntity != null)
		{
			playerEntity.setTeam(null);
			playerEntity.setStatus(false);
		}
		System.out.println("after player " + playerEntity);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
	
	/**
	 * Unassigns a team's manager when the team is removed
	 * @param teamName The name of the team that is to be removed
	 */
	public static void removeTeamsManagerEntity(String teamName)
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Soccer League Application");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Team teamEntity = entitymanager.find(Team.class, teamName);
		System.out.println("TEAM ENTITY BEFORE: " + teamEntity);
		if(teamEntity != null)
		{
			teamEntity.setManager(null);
			System.out.println("TEAM ENTITY BEFORE: " + teamEntity);
		}
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
}