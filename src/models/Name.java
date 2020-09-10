package models;

public class Name
{
  private String firstName;
  private String middleName;
  private String lastName;

  public Name(String firstName, String middleName, String lastName) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  public Name(String name) {
    String[] nameList = name.split(" ");
    if (nameList.length == 2) {
      this.firstName = nameList[0];
      this.middleName = "";
      this.lastName = nameList[1];
    } else {
      this.firstName = nameList[0];
      this.middleName = nameList[1];
      this.lastName = nameList[2];
    }
  }

  // ----------------
  // Getters
  // ----------------

  public String getFirstName() {
    return this.firstName;

  }

  public String getMiddleName() {
    return this.middleName;

  }

  public String getLastName() {
    return this.lastName;

  }

  // ----------------
  // Setters
  // ----------------

  public void setFirstName(String newFirstName) {
    this.firstName = newFirstName;
  }

  public void setMiddleName(String newMiddleName) {
    this.middleName = newMiddleName;
  }

  public void setLastName(String newLastName) {
    this.lastName = newLastName;
  }

  // ----------------
  // Methods
  // ----------------

  public String toString() {
    if (this.middleName.equals("")) {
      return this.firstName + " " + this.lastName;
    } else {
      return this.firstName + " " + this.middleName + " " + this.lastName;
    }
  }
}