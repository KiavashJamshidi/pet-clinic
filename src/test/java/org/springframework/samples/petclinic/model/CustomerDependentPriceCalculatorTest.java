package org.springframework.samples.petclinic.model;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CustomerDependentPriceCalculatorTest{
    private CustomerDependentPriceCalculator customerDependentPriceCalculator;
    private double BASE_RARE_COEF;
    private double baseCharge;
	private double BASE_PRICE_PER_PET;
    private double RARE_INFANCY_COEF;
	private double COMMON_INFANCY_COEF;
	private UserType newUser;
	private UserType goldUser;
	private UserType silverUser;

    @BeforeClass
    public void setUp(){
        this.customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
        this.BASE_RARE_COEF = 1.2;
        this.baseCharge = 100;
        this.BASE_PRICE_PER_PET = 40;
        this.RARE_INFANCY_COEF = 1.4;
        this.COMMON_INFANCY_COEF = 1.2;
        this.newUser = UserType.NEW;
        this.goldUser = UserType.GOLD;
        this.silverUser = UserType.SILVER;
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
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
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
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
		Date infantAge = new Date();

        when(pet.getType().getRare()).thenReturn(false);
        when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals(baseCharge + (BASE_PRICE_PER_PET * goldUser.discountRate * COMMON_INFANCY_COEF), calculatedPrice, 0.01);
	}


    @Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsInfantAndUserIsSilver() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
		Date infantAge = new Date();

        when(pet.getType().getRare()).thenReturn(false);
        when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * COMMON_INFANCY_COEF, calculatedPrice, 0.01);
	}

    @Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsNotRareAndPetIsNotInfantAndUserIsSilver() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
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
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
        Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		Date adultAge = calendar.getTime();

        when(pet.getType().getRare()).thenReturn(true);
        when(pet.getBirthDate()).thenReturn(adultAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * BASE_RARE_COEF, calculatedPrice, 0.01);
	}

	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsNotInfantAndUserIsGold() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
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
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
		Date infantAge = new Date();

        when(pet.getType().getRare()).thenReturn(true);
        when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(BASE_PRICE_PER_PET * BASE_RARE_COEF * RARE_INFANCY_COEF, calculatedPrice, 0.01);
	}

    @Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsOneAndPetIsRareAndPetIsInfantAndUserIsGold() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);
		Date infantAge = new Date();

        when(pet.getType().getRare()).thenReturn(true);
        when(pet.getBirthDate()).thenReturn(infantAge);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals((BASE_PRICE_PER_PET * BASE_RARE_COEF * RARE_INFANCY_COEF * goldUser.discountRate) + baseCharge,
            calculatedPrice, 0.01);
	}

    @Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsTenAndPetsAreNotRareAndPetIsNotInfantAndUserIsGold() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
        Date adultAge = calendar.getTime();
        pet.setBirthDate(adultAge);

        when(pet.getType().getRare()).thenReturn(true);

        for (int i=0; i<10; i++) {
            pets.add(pet);
        }

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, goldUser);

		assertEquals((BASE_PRICE_PER_PET * pets.size() * goldUser.discountRate) + baseCharge, calculatedPrice, 0.01);
	}

    @Test
	public void should_returnSpecifidPrice_when_lengthOfPetsIsTenAndPetsAreNotRareAndPetIsNotInfantAndUserIsNew() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
        Date adultAge = calendar.getTime();
        pet.setBirthDate(adultAge);

        when(pet.getType().getRare()).thenReturn(true);

        for (int i=0; i<10; i++)
            pets.add(pet);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, newUser);

		assertEquals((BASE_PRICE_PER_PET * pets.size() * newUser.discountRate) + baseCharge, calculatedPrice, 0.01);
	}
    
	public void should_returnSpecifidPrice_when_lengthOfPetsIsTenAndPetsAreNotRareAndPetIsNotInfantAndUserIsSilver() {
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
        Date adultAge = calendar.getTime();
        pet.setBirthDate(adultAge);

        when(pet.getType().getRare()).thenReturn(true);

        for (int i=0; i<10; i++)
            pets.add(pet);

		double calculatedPrice = customerDependentPriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, silverUser);

		assertEquals(((BASE_PRICE_PER_PET * pets.size()) + baseCharge) * silverUser.discountRate , calculatedPrice, 0.01);
	}

    @AfterClass
    public void tearDown(){
        this.customerDependentPriceCalculator = null;
    }
}