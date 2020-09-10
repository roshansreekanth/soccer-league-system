package controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.apache.derby.tools.dblook;

import models.League;
import models.Manager;
import models.Person;
import models.Player;
import models.Team;

public class Controller 
{
	/**
	 * Adds data into the database
	 * @param userObject Object to be added
	 */
	public void add(Object userObject)
	{
		try
		{
			DBOperations.create(userObject);
		}
		
		catch (Exception DerbySQLIntegrityConstraintViolationException) {
			System.out.println("ALREADY EXISTS");
		}
	}
	
	/**
	 * Removes data from the database
	 * @param userObject Object to be removed from the database
	 */
	public void remove(Object userObject)
	{
		DBOperations.remove(userObject);
	}
	
	/**
	 * Get a List of elements from a table
	 * @param tableName The table from which to get data from
	 * @return Returns a list containing objects from the specified table
	 */
	public List get(String tableName)
	{
		return  DBOperations.getAll(tableName);
	}
	
	/**
	 * Get a list of all elements from a table based on a specific condition
	 * @param tableName The table from which to fetch data
	 * @param conditionalColumn The column on which the data fetched is based on
	 * @param conditionalValue The value of the conditional column
	 * @return Returns a list containing objects from the specified table
	 */
	public List getConditional(String tableName, String conditionalColumn, String conditionalValue)
	{
		return DBOperations.getConditional(tableName, conditionalColumn, conditionalValue);
	}
	
	
	/**
	 * Assigns a manager to a team
	 * @param teamManagerEmail The email of the manager to be assigned
	 * @param teamName The name of the team the manager is assigned to
	 */
	public void addManagerToTeam(String teamManagerEmail, String teamName)
	{
		List managerList = DBOperations.getAll("Manager");
		for(Object m : managerList)
		{
			if(((Manager) m).getEmail().equals(teamManagerEmail) && ((Manager) m).isTaken())
			{
				System.out.println("Manager is already taken");
			}
			else 
			{
				DBOperations.updateManagerEntity(teamManagerEmail, teamName);
			}

		}
	}
	
	/**
	 * Assigns a team to a league
	 * @param teamName The name of the team to be added to the league
	 * @param leagueName The name of the league
	 */
	public void addTeamToLeague(String teamName, String leagueName)
	{
		List teamList = DBOperations.getAll("Team"); 
		for(Object t : teamList)
		{
			if(((Team) t).getName() == teamName && ((Team) t).isTaken())
			{
				System.out.println(teamName + " is already in a league");
				return;
			}	
		}
		DBOperations.updateTeamLeagueEntity(teamName, leagueName);
	}
	
	/**
	 * Adds a team to the database
	 * @param teamName The name of the team
	 * @param teamColour The team's jersey color
	 * @param teamLeagueName The league that the team is in
	 * @param teamManagerEmail The email of the manager assigned to the team
	 */
	public void processTeam(String teamName, String teamColour, String teamLeagueName, String teamManagerEmail) 
	{
		List managerList = DBOperations.getAll("Manager");
		for(Object m : managerList)
		{
			if(((Manager) m).getEmail().equals(teamManagerEmail) && ((Manager) m).isTaken())
			{
				System.out.println("Manager is already taken");
				return;
			}
		}
		
		List teamList = DBOperations.getAll("Team");
		for(Object t : teamList)
		{
			if(((Team) t).getName().equals(teamName))
			{
				System.out.println("Team Name is not unique");
				return;
			}
			
			if(((Team) t).getColour().equals(teamColour))
			{
				System.out.println("Team colour is not unique");
				return;
			}
		}
		
		Team teamObject = new Team(teamName, teamColour);
		teamObject.setManager(teamManagerEmail);
		teamObject.setStatus(true);
		add(teamObject);
		addManagerToTeam(teamManagerEmail, teamName);
		addTeamToLeague(teamName, teamLeagueName);
	}
	
	/**
	 * Adds a player to the database
	 * @param playerObject The playerObject to be added
	 * @param playerTeamName The team the player is assigned to
	 */
	public void processPlayer(Player playerObject, String playerTeamName)
	{
		
		List playerList = DBOperations.getAll("Player");
		for(Object p : playerList)
		{
			if(((Player) p).getName().equals(playerObject.getName()) && ((Player) p).isTaken())
			{
			System.out.println("Player is already in a team");
			return;
			}
		}
		playerObject.setTeam(playerTeamName);
		playerObject.setStatus(true);
		add(playerObject);
	}
	
	/**
	 * Removes a league and it's corresponding teams from the database
	 * @param leagueObject The league object to be removed
	 */
	public void processLeagueRemoval(League leagueObject)
	{
		List teamList = DBOperations.getAll("Team");
		for(Object t : teamList)
		{
			if(((Team) t).getLeague() != null && ((Team) t).getLeague().equals(leagueObject.getName()))
			{
				DBOperations.updateTeamLeagueEntity(((Team) t).getName(), null);
			}
		}
		remove(leagueObject);
	}
	
	/**
	 * Removes a team from the database
	 * @param teamObject The team object to be removed
	 */
	public void processTeamRemoval(Team teamObject)
	{
		if(teamObject.getLeague() != null)
		{
			DBOperations.updateTeamLeagueEntity(teamObject.getName(), null);
		}
		
		List playerList = DBOperations.getAll("Player");
		for(Object p : playerList)
		{
			System.out.println("Player" + p);
			if(((Player) p).getTeam() != null && ((Player) p).getTeam().equals(teamObject.getName()))
			{
				System.out.println((Player) p);
				DBOperations.removePlayersTeamEntity(((Player) p).getEmail());
			}
		}
		
		List managerList = DBOperations.getAll("Manager");
		for(Object m : managerList)
		{
			if(((Manager) m).getTeam() != null && ((Manager) m).getTeam().equals(teamObject.getName()))
			{
				DBOperations.updateManagerEntity(((Manager) m).getEmail(), null);
			}
		}
		
		DBOperations.removeTeamsManagerEntity(teamObject.getName());
		teamObject.setManager(null);
		teamObject.setStatus(false);
		DBOperations.remove(teamObject);
	}

	/**
	 * Removes a manager from the database (including cutting off ties from the team)
	 * @param managerObject The manager object to be removed
	 */
	public void processManagerRemoval(Manager managerObject)
	{
		if(managerObject.getTeam() != null)
		{
			DBOperations.removeTeamsManagerEntity(managerObject.getTeam());
			DBOperations.updateManagerEntity(managerObject.getEmail(), null);
			managerObject.setTeam(null);
			managerObject.setStatus(false);
			remove(managerObject);
		}
		else
		{
			remove(managerObject);
		}
	}
	
	/**
	 * Removes a player form the database
	 * @param playerObject The player object to be removed
	 */
	public void processPlayerRemoval(Player playerObject)
	{
		DBOperations.removePlayersTeamEntity(playerObject.getEmail());
		System.out.println("Updated player object: " + playerObject);
		playerObject.setTeam(null);
		remove(playerObject);
	}
}
