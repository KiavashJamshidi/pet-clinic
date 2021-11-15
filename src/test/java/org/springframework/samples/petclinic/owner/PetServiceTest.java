package org.springframework.samples.petclinic.owner;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.After;

@RunWith(SpringRunner.class) @SpringBootTest
class PetServiceTest {
    @Autowired PetService petService;
    public Owner owner = new Owner();

	@ParameterizedTest @ValueSource(
        ints = {13,7,9,1,2,-18,4,9,-1}
    )

    @Test
    public void findPetTest(int petId) {
        assumeNotNull(petId);
        assertTrue("Find pet with id " + petId + " does not work properly!",petService.findPet(petId) != null);
    }

    @After
    public void tearDown(){
        this.owner = null;
    }
}