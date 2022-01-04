package bdd;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindOwnerSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner1;
	private Owner foundOwner;

	private String ownerName = "Sina";
	private String ownerLastName = "Salimian";
	private String ownerAddress = "Ferdos";
	private String ownerCity = "Tehran";
	private String ownerCellPhone = "09120136383";

	@Given("There is an owner with id {string}")
	public void thereIsAPetOwnerWithID(String ID) {
		owner1 = new Owner();
		owner1.setId(Integer.valueOf(ID));
		owner1.setFirstName(ownerName);
		owner1.setLastName(ownerLastName);
		owner1.setAddress(ownerAddress);
		owner1.setCity(ownerCity);
		owner1.setTelephone(ownerCellPhone);
		ownerRepository.save(owner1);
	}

	@When("Tried to find the owner")
	public void theOwnerWasCalledByID() {
		foundOwner = petService.findOwner(owner1.getId());
	}

	@Then("The owner is found properly")
	public void ownerFound() {
		assertEquals(owner1.getId(), foundOwner.getId());
	}
}
