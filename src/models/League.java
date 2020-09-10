package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class League
{
  @Id
  private String leagueName;
  
  public League() {} // Default no-argument constructor for making a JPA Entity
  
  public League(String name)
  {
    this.leagueName = name;
  }

 //----------------
 //  Getters
 //----------------
  public String getName()
  {
    return this.leagueName;
  }
  
  public String toString()
  {
	  return this.leagueName;
  }
}