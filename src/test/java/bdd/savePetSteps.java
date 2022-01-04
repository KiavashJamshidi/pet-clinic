package bdd;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class savePetSteps {
	@Autowired
	PetService petService;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private Pet pet;
	private Owner owner;
	private PetType petType;

	@Given("There is a pet owner called {string}")
	public void thereIsAnOwnerCalled(String name) {
		owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName("Salimian");
		owner.setAddress("Ferdos");
		owner.setCity("Tehran");
		owner.setTelephone("09120136383");
		ownerRepository.save(owner);
	}

	@When("He performs save pet service to add a pet to his list")
	public void aNewPetIsSavedForTheOwner() {
		pet = new Pet();
		petType = new PetType();
		petType.setName("Dog");
		petTypeRepository.save(petType);
		pet.setType(petType);
		petService.savePet(pet, owner);
	}

	@Then("The pet is saved successfully")
	public void thePetIsSavedSuccessfully() {
		assertNotNull(petService.findPet(petType.getId()));	}
}
