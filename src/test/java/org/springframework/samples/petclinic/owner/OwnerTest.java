package org.springframework.samples.petclinic.owner;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import java.util.*;

@RunWith(Theories.class)
class OwnerTest {
    private Owner owner;

    @BeforeEach
    public void setUp(){
        owner = new Owner();
    }

    @DataPoints("namesOfPets")
	public static List<String> namesOfPets() {
		return asList("cat","dog","bat","elephant","snake","spider","giraffe","snake","haskey");
	}

    @DataPoints
	public static String[] pets = {"cat", "dog"};

	@DataPoints
	public static ArrayList[] setOfPets = {
        new ArrayList<>(),
        new ArrayList<>(Arrays.asList("cat", "horse", "dainasour")),
        new ArrayList<>(Arrays.asList("bird", "cockroach")),
        new ArrayList<>(Arrays.asList("bat", "spider")),
        new ArrayList<>(Arrays.asList("tiger", "snake", "lion"))
    };

    @Test
    public void testAddress(){
        owner.setAddress("Amirabad");
        assertEquals("get/setAddress or setAddress doesn't work properly!", owner.getAddress(), "Enghelab");
    }

    @Test
    public void testCity(){
        owner.setCity("Tehran");
        assertEquals("get/setCity or setCity doesn't work properly!", owner.getCity(), "Tehran");
    }

    @Test
    public void testTelephone(){
        owner.setTelephone("02188220480");
        assertEquals("get/setTelephone or setTelephone doesn't work properly!", owner.getTelephone(), "02188220480");
    }

    @Test
    public void testGetPet(){
        Pet pet = new Pet();
        pet.setName("Cat");
        owner.addPet(pet);

        Pet anotherPet = new Pet();
        anotherPet.setName("Dog");

        assertEquals("get/setPet doesn't work properly!", owner.getPet("Cat"), pet);
    }

    @Test
    public void testPetsInternal(){
        Pet pet = new Pet();
        pet.setName("Hello");

        Set<Pet> pets = new HashSet<>();
        
        pets.add(pet);
        pets.add(pet);

        owner.setPetsInternal(pets);

        assertEquals("get/setPetsInternal doesn't work properly!", owner.getPetsInternal() , pets);
    }

    @Test
    public void testRemovePet(){
        Pet pet = new Pet();
        pet.setName("Cat");

        Pet anotherPet = new Pet();
        anotherPet.setName("Dog");

        owner.addPet(pet);
        owner.addPet(anotherPet);
        owner.removePet(pet);

        assertTrue("removePet doesn't work properly!", !owner.getPetsInternal().contains(pet));
    }

    @Test
    public void testAddPet(){
        Pet pet = new Pet();
        pet.setName("Cat");

        owner.addPet(pet);
        assertTrue("addPet doesn't work properly!", owner.getPetsInternal().contains(pet));
    }

    @Test
    public void testGetPets(){
        Pet pet = new Pet();
        pet.setName("Cat");

        Set<Pet> pets = new HashSet<>();
        
        pets.add(pet);
        pets.add(pet);

        owner.setPetsInternal(pets);

        assertTrue("get/setPetsInternal doesn't work properly!", owner.getPets().containsAll(pets));
    }

    @Theory
	public void testGetPet(String pet, ArrayList<String> setOfPets){
		assumeNotNull(pet);
        assumeTrue(pet.length() > 0);
		assumeNotNull(setOfPets);
		assumeTrue(setOfPets.size() > 0);
        assumeTrue(setOfPets.contains(pet));

        Pet tempPet;
		for(String petName : setOfPets){
			tempPet = new Pet();
			tempPet.setName(petName);
			setOfPets.add(petName);
            owner.addPet(tempPet);
		}
        String ownerPetName = owner.getPet(pet).getName();
		assertTrue("GetPet does not work properly",pet.equals(ownerPetName));
	}

    @Test
    public void getPetIgnore(){
        Pet pet = new Pet();
        pet.setName("dog");
        owner.addPet(pet);
		assertEquals(owner.getPet("dog", false) , pet);
        assertNull(owner.getPet("dog", true));

        pet.setName("crocodile");
        owner.addPet(pet);
		assertEquals(owner.getPet("crocodile", false) , pet);
        assertNull(owner.getPet("crocodile", true));
        
        pet = owner.getPet("crocodile", false);
        assumeNotNull(pet);
        pet.setId(0);
        assertEquals(owner.getPet("horse", true),pet);
    }

    @AfterEach
    public void tearDown(){
        owner = null;
    }
}
