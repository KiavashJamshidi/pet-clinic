package org.springframework.samples.petclinic.model;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimplePriceCalculatorTest {
	private static double BASE_RARE_COEF;
	private static double baseCharge;
	private static double BASE_PRICE_PER_PET;
	private static UserType newUser;
	private static UserType oldUser ;
	private static SimplePriceCalculator simplePriceCalculator;
	private static Pet pet;
	private static PetType petType;

	@BeforeClass
	public static void setUp(){
		BASE_RARE_COEF = 1.2;
		baseCharge = 100;
		BASE_PRICE_PER_PET = 40;
		newUser = UserType.NEW;
		oldUser = UserType.SILVER;
		simplePriceCalculator = new SimplePriceCalculator();
		pet = mock(Pet.class) ;
		petType= mock(PetType.class);
		when(pet.getType()).thenReturn(petType);

	}

	@Test
	//Test Path 1 2 3 8 10
	public void should_returnBaseCharge_when_petsIsEmptyAndUserIsOld(){
		List<Pet> emtpyList = Collections.emptyList();
		double calculatedPrice = simplePriceCalculator.calcPrice(emtpyList, baseCharge, BASE_PRICE_PER_PET, oldUser);
		assertEquals(this.baseCharge, calculatedPrice, 0.1);
	}

	@Test
	//Test Path 1 2 3 8 9 10
	public void should_returnBaseChargeMultDiscountRate_when_petsIsEmptyAndUserIsNew(){
		List<Pet> emtpyList = Collections.emptyList();
		double calculatedPrice = simplePriceCalculator.calcPrice(emtpyList, baseCharge, BASE_PRICE_PER_PET, newUser);
		assertEquals(baseCharge * newUser.discountRate, calculatedPrice, 0.1);
	}


	@Test
	//Test Path 1 2 3 4 5 7 3 8 10
	public void should_returnExpectedPrice_when_LengthOfPetsIsOneAndUserIsOld(){
		List<Pet> pets = Arrays.asList(pet);
		when(pet.getType().getRare()).thenReturn(true);

		double calculatedPrice = simplePriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, oldUser);

		assertEquals(baseCharge + BASE_PRICE_PER_PET * BASE_RARE_COEF, calculatedPrice, 0.1);
	}

	@Test
	//Test Path 1 2 3 4 6 7 3 8 10
	public void should_returnExpectedPrice_when_LengthOfPetsIsOneAndUserIsNew(){
		List<Pet> pets = Arrays.asList(pet);
		when(pet.getType().getRare()).thenReturn(false);

		double calculatedPrice = simplePriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, newUser);

		assertEquals((baseCharge + BASE_PRICE_PER_PET) * newUser.discountRate, calculatedPrice, 0.01);
	}

	@AfterClass
	public static void tearDown(){
		simplePriceCalculator = null;
	}
}
