package models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Player extends Person
{
  private boolean goalieStatus;
  private int numGoals;
  private boolean inTeam;
  private String teamName;
  
  public Player() {} // Default no-argument constructor for making a JPA Entity
  
  public Player(String name, String phone, String email, int numGoals, boolean goalieStatus)
  {
    super(name, phone, email);
    this.numGoals = numGoals;
    this.goalieStatus = goalieStatus;
    this.inTeam = false;
  }

  //----------------
  //  Getters
  //----------------

  public int getNumGoals()
  {
    return this.numGoals;
  }

  public boolean getGoalieStatus()
  {
    return this.goalieStatus;
  }

  public String getTeam()
  {
    return this.teamName;
  }
 //----------------
 //  Setters
 //----------------

 public void setNumGoals(int newGoals)
 {
   this.numGoals = newGoals;
 }

 public void setGoalieStatus(boolean newGoalieStatus)
 {
   this.goalieStatus = newGoalieStatus;
 }

 public void setStatus(boolean newStatus)
 {
   this.inTeam = newStatus;
 }

public void setTeam(String teamObj)
{
  this.teamName = teamObj;
}
 //----------------
 //  Methods
 //----------------
 public boolean isTaken()
 {
    return this.inTeam;
 }
 
 public String toString()
 {
	 String returnString = super.toString() + " Team : " + this.teamName + " Goals: " + this.numGoals + " Goalie: " + this.goalieStatus;
	 return returnString;
 }
 
}