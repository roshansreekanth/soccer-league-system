package controller;

import java.util.Comparator;

import models.Person;

public class PersonNameComparator implements Comparator<Person>
{
	public int compare(Person p1, Person p2)
	{
		String p1Name = p1.getName();
		String p2Name = p2.getName();
		return p1Name.compareTo(p2Name);
	}
}
