package bdd;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class newPetSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private Pet newPet;

	@Given("There is an owner called {string}")
	public void thereIsAPetOwnerCalled(String name) {
		owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName("Salimian");
		owner.setAddress("Ferdos");
		owner.setCity("Tehran");
		owner.setTelephone("09120136383");
		ownerRepository.save(owner);
	}

	@When("A new Pet is set for the owner")
	public void newPetIsSetForTheOwner() {
		newPet = petService.newPet(owner);
	}

	@Then("The owner has the new pet")
	public void petIsAddedToTheOwner() {
		assertEquals(owner.getId(), newPet.getOwner().getId());
	}
}
