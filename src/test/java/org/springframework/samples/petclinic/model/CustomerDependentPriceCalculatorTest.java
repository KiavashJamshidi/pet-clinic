package org.springframework.samples.petclinic.model;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CustomerDependentPriceCalculatorTest{
	private static CustomerDependentPriceCalculator customerDependentPriceCalculator;
	private static double BASE_RARE_COEF;
	private static double baseCharge;
	private static double BASE_PRICE_PER_PET;
	private static double RARE_INFANCY_COEF;
	private static double COMMON_INFANCY_COEF;
	private static UserType newUser;
	private static UserType goldUser;
	private static UserType silverUser;
	private static Pet pet;
	private static PetType petType;

	@BeforeClass
	public static void setUp(){
		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
		BASE_RARE_COEF = 1.2;
		baseCharge = 100;
		BASE_PRICE_PER_PET = 40;
		RARE_INFANCY_COEF = 1.4;
		COMMON_INFANCY_COEF = 1.2;
		newUser = UserType.NEW;
		goldUser = UserType.GOLD;
		silverUser = UserType.SILVER;
		pet = mock(Pet.class) ;
		petType= mock(PetType.class);
		when(pet.getType()).thenReturn(petType);

	}

	@Test
	public void should_returnZero_when_petsIsEmptyAndUserIsSilver() {
		List<Pet> emtpyList = Collections.emptyList();

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(emtpyList, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(0, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnBaseCharge_when_petsIsEmptyAndUserIsGold() {
		List<Pet> emtpyList = Collections.emptyList();

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(emtpyList, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals(baseCharge, calculatedPrice, 0.01);
	}


	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsNotInfantAndUserIsGold() {
		List<Pet> pets = Arrays.asList(pet);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();

		when(pet.getType().getRare()).thenReturn(false);
		when(pet.getBirthDate()).thenReturn(adultAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals(baseCharge + (BASE_PRICE_PER_PET * goldUser.discountRate), calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsInfantAndUserIsGold() {
		List<Pet> pets = Arrays.asList(pet);
		Date infantAge = new Date();

		when(pet.getType().getRare()).thenReturn(false);
		when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals(baseCharge + (BASE_PRICE_PER_PET * goldUser.discountRate * COMMON_INFANCY_COEF), calculatedPrice, 0.01);
	}


	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsInfantAndUserIsSilver() {
		List<Pet> pets = Arrays.asList(pet);
		Date infantAge = new Date();

		when(pet.getType().getRare()).thenReturn(false);
		when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * COMMON_INFANCY_COEF, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsNotInfantAndUserIsSilver() {
		List<Pet> pets = Arrays.asList(pet);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();

		when(pet.getType().getRare()).thenReturn(false);
		when(pet.getBirthDate()).thenReturn(adultAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsNotInfantAndUserIsSilver() {
		List<Pet> pets = Arrays.asList(pet);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();

		when(pet.getType().getRare()).thenReturn(true);
		when(pet.getBirthDate()).thenReturn(adultAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * BASE_RARE_COEF, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsNotInfantAndUserIsGold() {
		List<Pet> pets = Arrays.asList(pet);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();

		when(pet.getType().getRare()).thenReturn(true);
		when(pet.getBirthDate()).thenReturn(adultAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals((BASE_PRICE_PER_PET * BASE_RARE_COEF * goldUser.discountRate) + baseCharge, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsInfantAndUserIsSilver() {
		List<Pet> pets = Arrays.asList(pet);
		Date infantAge = new Date();

		when(pet.getType().getRare()).thenReturn(true);
		when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * BASE_RARE_COEF * RARE_INFANCY_COEF, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsInfantAndUserIsGold() {
		List<Pet> pets = Arrays.asList(pet);
		Date infantAge = new Date();

		when(pet.getType().getRare()).thenReturn(true);
		when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals((BASE_PRICE_PER_PET * BASE_RARE_COEF * RARE_INFANCY_COEF * goldUser.discountRate) + baseCharge,
			calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifiedPrice_when_lengthOfPetsIsTenAndPetsAreNotRareAndPetIsNotInfantAndUserIsNew() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();
		when(pet.getType().getRare()).thenReturn(false);
		when(pet.getBirthDate()).thenReturn(adultAge);
		List<Pet> pets = Arrays.asList(pet, pet, pet, pet, pet, pet, pet, pet, pet, pet);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, newUser);

		assertEquals((BASE_PRICE_PER_PET * pets.size() * newUser.discountRate) + baseCharge, calculatedPrice, 0.01);
	}

	@Test
	public void should_returnSpecifiedPrice_when_lengthOfPetsIsTenAndPetsAreNotRareAndPetIsNotInfantAndUserIsSilver() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();
		when(pet.getBirthDate()).thenReturn(adultAge);
		when(pet.getType().getRare()).thenReturn(false);

		List<Pet> pets = Arrays.asList(pet, pet, pet, pet, pet, pet, pet, pet, pet, pet);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(((BASE_PRICE_PER_PET * pets.size()) + baseCharge) * silverUser.discountRate , calculatedPrice, 0.01);
	}

	@AfterClass
	public static void tearDown(){
		customerDependentPriceCalculator = null;
	}
}
