package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Team
{
  @Id
  private String teamName;
  private String teamColour;
  private String email; 
  private boolean inLeague;
  private String leagueName;
  
  public Team() {} // Default no-argument constructor for making a JPA Entity
  
  public Team(String name, String colour)
  {
    this.teamName = name;
    this.teamColour = colour.toLowerCase();
    this.email = null;
    this.leagueName = null;
    this.inLeague = false;
  }

 //----------------
 //  Getters
 //----------------
  public String getName()
  {
    return this.teamName;
  }

  public String getColour()
  {
    return this.teamColour;
  }

  public boolean isTaken()
  {
    return this.inLeague;
  }

  public String getLeague()
  {
    return this.leagueName;
  }

  public String getManager()
  {
    return this.email;
  }

 //----------------
 //  Setters
 //----------------

  public void setName(String newName)
  {
    this.teamName = newName;
  }

  public void setColour(String newColour)
  {
    this.teamName = newColour.toLowerCase();
  }

  public void setStatus(boolean newStatus)
  {
    this.inLeague = newStatus;
  }

  public void setLeague(String leagueName)
  {
    this.leagueName = leagueName; 
  }
  
  public void setManager(String managerEmail)
  {
    this.email = managerEmail;
  }
  
  public String toString()
  {
	  return "Name: " + this.getName() + " Manager: " + this.getManager() + " Colour: " + this.getColour() + " League : " + this.getLeague();
  }

}
