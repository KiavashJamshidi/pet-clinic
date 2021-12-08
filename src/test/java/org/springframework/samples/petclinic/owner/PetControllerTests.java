package org.springframework.samples.petclinic.owner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(value = PetController.class, includeFilters = {
	@ComponentScan.Filter(value=PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	@ComponentScan.Filter(value=LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
	@ComponentScan.Filter(value=PetService.class, type = FilterType.ASSIGNABLE_TYPE),
	@ComponentScan.Filter(value=PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),})

class PetControllerTests {
	private int petID = 1;
	private int ownerID = 10;
	private String petName = "Elephant";
	private String ownerName = "Sina";
	private String dateOfBirth = "1999-11-01";

	private Pet pet;
	private PetType samplePetType;

	@MockBean
	private OwnerRepository ownerRepo;
	@MockBean
	private PetRepository petRepo;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		pet = new Pet();
		samplePetType = new PetType();

		pet.setId(petID);
		samplePetType.setId(2);
		samplePetType.setName(petName);

		given(petRepo.findPetTypes()).willReturn(Lists.newArrayList(samplePetType));
		given(ownerRepo.findById(this.ownerID)).willReturn(new Owner());
		given(petRepo.findById(1)).willReturn(pet);
	}

	@Test
	public void TestInitCreationForm_OK() throws Exception {
		mockMvc.perform(get("/owners/"+ownerID+"/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void TestInitCreationForm_PostMethodError() throws Exception {
		mockMvc.perform(post("/owners/"+ownerID+"/pets/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void TestProcessCreationForm_OK() throws Exception {
		mockMvc.perform(post("/owners/"+ownerID+"/pets/new")
			.param("type", petName)
			.param("name", ownerName)
			.param("birthDate", dateOfBirth))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void TestInitUpdateForm_OK() throws Exception {
		mockMvc.perform(get("/owners/"+ownerID+"/pets/"+petID+"/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void TestProcessUpdateForm_OK() throws Exception {
		mockMvc.perform(post("/owners/"+ownerID+"/pets/"+petID+"/edit")
			.param("type", petName)
			.param("name", ownerName)
			.param("birthDate", dateOfBirth))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void TestProcessUpdateForm_NoParamError() throws Exception {
		mockMvc.perform(post("/owners/"+ownerID+"/pets/"+petID+"/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@AfterEach
	public void tearDown() {
		pet = null;
		samplePetType = null;
	}
}
