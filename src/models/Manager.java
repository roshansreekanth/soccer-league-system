package models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Manager extends Person
{
  private String birthDate;
  private boolean managerTaken;
  private float rating;
  private String teamName;
  
  public Manager() {}
  
  public Manager(String name, String phone, String email, LocalDate birthDate, float rating) {
    super(name, phone, email);
    this.birthDate = birthDate.toString();
    this.rating = rating;
    this.managerTaken = false;
    this.teamName = null;
  }

  // ----------------
  // Getters
  // ----------------

  public float getRating() {
    return this.rating;
  }

  public boolean isTaken() {
    return this.managerTaken;
  }

  public String getTeam() {
    return this.teamName;
  }

  public String getBirthDate() {
    return this.birthDate;
  }
  // ----------------
  // Setters
  // ----------------

  public void setRating(float newRating) {
    this.rating = newRating;
  }

  public void setStatus(boolean newStatus) {
    this.managerTaken = newStatus;
  }

  public void setTeam(String teamObject) {
    this.teamName = teamObject;
  }
  
  public String toString()
  {
	  return super.toString() + " DOB: " + this.birthDate + " Team: " + this.teamName + " Rating " + this.rating;
  }

}