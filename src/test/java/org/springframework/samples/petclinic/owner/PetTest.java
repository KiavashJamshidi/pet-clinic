package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assume.*;


@RunWith(Theories.class)
public class PetTest {
	private Pet pet;

	@Before
	public void setup() {
		pet = new Pet();
	}

	@DataPoints
	public static ArrayList[] date = { 
		new ArrayList<>(Arrays.asList(2000,12,3)),
		new ArrayList<>(Arrays.asList(2050,20,-4)),
		new ArrayList<>(Arrays.asList(2014,4,30)),
		new ArrayList<>(Arrays.asList(2100,3,25)),
		new ArrayList<>(Arrays.asList(-5,-1,0)),
		new ArrayList<>(Arrays.asList(1987,10,1)),
		new ArrayList<>(Arrays.asList(2012,7,19)),
		new ArrayList<>(Arrays.asList(1746,6,14)),
		new ArrayList<>(Arrays.asList(1825,5,10))
	};

	@Theory
	public void getVisitsTest(ArrayList<Integer> dates){
		ArrayList<Visit> visits = new ArrayList<>();
		assumeNotNull(dates);

		assumeTrue(dates.size() == 3);

		assumeNotNull(dates.get(0));
		assumeNotNull(dates.get(1));
		assumeNotNull(dates.get(2));

		int day = dates.get(2);
		int month = dates.get(1);
		int year = dates.get(0);

		assumeTrue(year > 0 && year <= 2021);
		assumeTrue(day > 0 && day < 31);
		assumeTrue(month > 0 && month <= 12);

		LocalDate locDate = LocalDate.of(year, month, day);
		Visit visit = new Visit();
		visit.setDate(locDate);

		visits.add(visit);
		this.pet.addVisit(visit);
		assertTrue("GetVisit does not work properly!",pet.getVisits().containsAll(visits) && pet.getVisits().size() == visits.size());

	}
}