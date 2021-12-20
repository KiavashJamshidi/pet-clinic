package org.springframework.samples.petclinic.utility;
import static java.time.temporal.ChronoUnit.DAYS;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.visit.Visit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceCalculatorTest {
	PriceCalculator priceCalc = new PriceCalculator();
	private static double BASE_CHARGE;
	private static double BASE_PRICE_PER_PET;
	private static double BASE_RARE_COEF;
	private static double RARE_INFANCY_COEF;
	private static double DISCOUNT_PRE_VISIT;
	private static Pet pet ;
	private static PetType petType;

	@Before
	public void setUp(){
		BASE_RARE_COEF = 1.2;
		BASE_CHARGE = 100;
		BASE_PRICE_PER_PET = 40;
		RARE_INFANCY_COEF = 1.4;
		DISCOUNT_PRE_VISIT = 2;
		pet = mock(Pet.class) ;
		petType= mock(PetType.class);
		when(pet.getType()).thenReturn(petType);
	}


	@Test
	public void emptyPetsTest(){
		List<Pet> emtpyList = Collections.emptyList();
		double calculatedPrice = priceCalc.calcPrice(emtpyList, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(0, calculatedPrice, 0.1);
	}


	@Test
	public void oneMaturePetAndDiscountCounterIsLessThanMinScoreTest(){
		LocalDate birthDate = LocalDate.of(2015,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(pet.getVisitsUntilAge(6)).thenReturn(new ArrayList<>());
		List<Pet> pets = Arrays.asList(pet);
		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(BASE_PRICE_PER_PET*BASE_RARE_COEF, calculatedPrice, 0.1);
	}

	@Test
	public void OneInfantPetAndDiscountCounterIsLessThanMinScoreTest(){
		LocalDate birthDate = LocalDate.of(2020,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(pet.getVisitsUntilAge(1)).thenReturn(new ArrayList<>());
		List<Pet> pets = Arrays.asList(pet);
		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(BASE_PRICE_PER_PET*BASE_RARE_COEF*RARE_INFANCY_COEF, calculatedPrice, 0.1);
	}


	@Test
	public void ThreeMaturePetsAndDiscountCounterIsLessThanMinScoreTest(){
		LocalDate birthDate = LocalDate.of(2015,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(pet.getVisitsUntilAge(6)).thenReturn(new ArrayList<>());
		List<Pet> pets = Arrays.asList(pet,pet,pet);
		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(BASE_PRICE_PER_PET*BASE_RARE_COEF*3, calculatedPrice, 0.1);
	}

	@Test
	public void TenMaturePetsAndDiscountIsGreaterThanMinScoreAndLastVisitLessThan100Test(){
		LocalDate birthDate = LocalDate.of(2015,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);

		Visit visit = new Visit();
		visit.setDate(LocalDate.of(2020,12,25));
		when(pet.getVisitsUntilAge(6)).thenReturn(Arrays.asList(visit,visit,visit));

		List<Pet> pets = Arrays.asList(pet,pet,pet,pet,pet,pet,pet,pet,pet,pet);
		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(3240, calculatedPrice, 0.1);
	}

	@Test
	public void ElevenMaturePetsAndDiscountIsGreaterThanMinScoreAndLastVisitLessThan100Test(){
		LocalDate birthDate = LocalDate.of(2015,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);

		Visit visit = new Visit();
		visit.setDate(LocalDate.of(2020,12,25));
		when(pet.getVisitsUntilAge(6)).thenReturn(Arrays.asList(visit,visit,visit));

		List<Pet> pets = Arrays.asList(pet,pet,pet,pet,pet,pet,pet,pet,pet,pet,pet);
		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		assertEquals(20088, calculatedPrice, 0.1);
	}

	@Test
	public void FiveInfantPetsAndDiscountCounterIsGreaterThanMinScoreAndLastVisitLessThan100Test(){
		LocalDate birthDate = LocalDate.of(2020,12,19);
		when(pet.getBirthDate()).thenReturn(birthDate);
		when(pet.getVisitsUntilAge(1)).thenReturn(new ArrayList<>());
		List<Pet> pets = Arrays.asList(pet,pet,pet,pet,pet);

		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		double expectedPrice = BASE_PRICE_PER_PET*BASE_RARE_COEF*RARE_INFANCY_COEF + 4*BASE_PRICE_PER_PET*BASE_RARE_COEF*RARE_INFANCY_COEF* DISCOUNT_PRE_VISIT + BASE_CHARGE;
		assertEquals(expectedPrice , calculatedPrice, 0.1);
	}

	@Test
	public void FiveTwoYearOldPetsAndDiscountCounterIsGreaterThanMinScoreAndLastVisitEqualTo100Test(){
		LocalDate birthDate = LocalDate.now().minusYears(2);
		when(pet.getBirthDate()).thenReturn(birthDate);
		Visit visit = new Visit();
		visit.setDate(LocalDate.now().minusDays(100));
		when(pet.getVisitsUntilAge(2)).thenReturn(Arrays.asList(visit));
		List<Pet> pets = Arrays.asList(pet,pet,pet,pet,pet);

		double calculatedPrice = priceCalc.calcPrice(pets, BASE_CHARGE, BASE_PRICE_PER_PET);
		long daysFromLastVisit = DAYS.between(visit.getDate(), LocalDate.now());
		double expectedPrice = BASE_PRICE_PER_PET*BASE_RARE_COEF*RARE_INFANCY_COEF + (4*BASE_PRICE_PER_PET*BASE_RARE_COEF*RARE_INFANCY_COEF + BASE_CHARGE) * (daysFromLastVisit / 100 + 1);
		assertEquals(expectedPrice , calculatedPrice, 0.1);
	}
}
