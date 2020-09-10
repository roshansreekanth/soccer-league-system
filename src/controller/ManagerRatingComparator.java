package controller;

import java.util.Comparator;

import models.Manager;

public class ManagerRatingComparator implements Comparator<Manager>
{
	public int compare(Manager m1, Manager m2)
	{
		float rating1 = m1.getRating();
		float rating2 = m2.getRating();
		return new Float(rating1).compareTo(new Float(rating2));
	}
}
