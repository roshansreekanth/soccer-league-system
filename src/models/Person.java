package models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person
{
    private String name;
    private String phone;
    @Id
    private String email;

    public Person() {} // // Default no-argument constructor for making a JPA Entity
    
    public Person(String name, String phone, String email) 
    {
      this.name = (new Name(name)).toString();
      this.phone = phone;
      this.email = email;
    }

  //----------------
  //  Getters
  //----------------

    public String getName()
    {
      return this.name;
    }

    public String getPhone()
    {
      return this.phone;
    }

    public String getEmail()
    {
      return this.email;
    }

  //----------------
  //  Setters
  //----------------

    public void setName(String newName)
    {
      this.name = newName;
    }

    public void setPhone(String newNumber)
    {
      this.phone = newNumber;
    }

  public void setEmail(String newMail)
  {
    this.email = newMail;
  }

  //----------------
  //  Setters
  //----------------

  public boolean compare(Person personObj)
  {
    if(this.email.equals(personObj.getEmail()) || this.phone.equals(personObj.getPhone()))
    {
      return true;
    }

    else
    {
      return false;
    }
  }

  public String toString()
  {
    return "Name: " + this.name + " Phone: " + this.phone + " Email: " + this.email;
  }
}