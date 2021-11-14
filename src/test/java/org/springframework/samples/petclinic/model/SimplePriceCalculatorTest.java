package org.springframework.samples.petclinic.model;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SimplePriceCalculatorTest {
	private double BASE_RARE_COEF;
    private double baseCharge;
	private double BASE_PRICE_PER_PET;
	private UserType newUser = UserType.NEW;
	private UserType oldUser = UserType.SILVER;
    private SimplePriceCalculator simplePriceCalculator;

    @BeforeClass
    public void setUp(){
        this.BASE_RARE_COEF = 1.1;
        this.baseCharge = 100;
        this.BASE_PRICE_PER_PET = 40;
        this.newUser = UserType.NEW;
        this.oldUser = UserType.SILVER;
        this.simplePriceCalculator = new SimplePriceCalculator();
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
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);

        when(pet.getType().getRare()).thenReturn(true);

        double calculatedPrice = simplePriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, oldUser);
        
        assertEquals(baseCharge + BASE_PRICE_PER_PET * BASE_RARE_COEF, calculatedPrice, 0.1);
    }

    @Test
    //Test Path 1 2 3 4 6 7 3 8 10
    public void should_returnExpectedPrice_when_LengthOfPetsIsOneAndUserIsNew(){
        List<Pet> pets = Collections.emptyList();
        Pet pet = new Pet();
        pets.add(pet);

        when(pet.getType().getRare()).thenReturn(false);

        double calculatedPrice = simplePriceCalculator.calcPrice(pets, baseCharge, BASE_PRICE_PER_PET, newUser);
        
        assertEquals(baseCharge + BASE_PRICE_PER_PET, calculatedPrice, 0.1);
    }

    @AfterClass
    public void tearDown(){
        this.simplePriceCalculator = null;
    }
}
