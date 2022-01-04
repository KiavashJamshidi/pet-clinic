package bdd;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPetSteps {
	@Autowired
	PetService petService;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private Pet pet;
	private Pet FoundPet;
	private Owner owner;
	private PetType petType = new PetType();
	private LocalDate localDate;

	@Given("There is an owner")
	public void thereIsAPetWithID() {
		owner = new Owner();
		owner.setFirstName("Sina");
		owner.setLastName("Salimian");
		owner.setAddress("Ferdos");
		owner.setCity("Tehran");
		owner.setTelephone("09120136383");
		ownerRepository.save(owner);
	}

	@And("There is a pet with id {string}")
	public void thereIsAnOwner(String ID) {
		petType.setName("Dog");
		petTypeRepository.save(petType);
		localDate = LocalDate.of(2018, 4,5);
		pet = new Pet();
		pet.setName("Jessie");
		pet.setId(Integer.valueOf(ID));
		pet.setBirthDate(localDate);
		pet.setType(petType);
		owner.addPet(pet);
		petRepository.save(pet);
	}

	@When("Tried to find the pet")
	public void thePetWasCalledByID() {
		FoundPet = petService.findPet(pet.getId());
	}

	@Then("The pet is found successfully")
	public void petFound() {
		assertEquals(pet.getId(), FoundPet.getId());
	}
}
