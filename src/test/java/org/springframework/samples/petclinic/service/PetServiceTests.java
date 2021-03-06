/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.AnimalshelterRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetServiceTests {  
	  @Autowired
		protected AnimalshelterRepository animalshelterRepository;
        @Autowired
	protected PetService petService;
        
        @Autowired
	protected OwnerService ownerService;	

	@Autowired
	protected AnimalshelterService	animalshelterService;

	@Test
	@Transactional
	void shouldFindPetWithCorrectId() {
		Pet pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).startsWith("Samantha");
		assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");

	}

	@Test
	@Transactional
	void shouldFindAllPetTypes() {
		Collection<PetType> petTypes = this.petService.findPetTypes();

		PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
		assertThat(petType1.getName()).isEqualTo("cat");
		PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
		assertThat(petType4.getName()).isEqualTo("snake");
	}

	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		int found = owner6.getPets().size();

		Pet pet = new Pet();
		pet.setName("bowser");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		pet.setGenre("male");
		owner6.addPet(pet);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);

            try {
                this.petService.savePet(pet);
            } catch (DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.ownerService.saveOwner(owner6);

		owner6 = this.ownerService.findOwnerById(6);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingPetsWithTheSameName() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		Pet pet = new Pet();
		pet.setName("wario");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		pet.setGenre("male");
		owner6.addPet(pet);
		try {
			petService.savePet(pet);		
		} catch (DuplicatedPetNameException e) {
			// The pet already exists!
			e.printStackTrace();
		}
		
		Pet anotherPetWithTheSameName = new Pet();		
		anotherPetWithTheSameName.setName("wario");
		anotherPetWithTheSameName.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPetWithTheSameName.setBirthDate(LocalDate.now().minusWeeks(2));
		
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			owner6.addPet(anotherPetWithTheSameName);
			petService.savePet(anotherPetWithTheSameName);
		});		
	}

	@Test
	@Transactional
	public void shouldUpdatePetName() throws Exception {
		Pet pet7 = this.petService.findPetById(7);
		String oldName = pet7.getName();

		String newName = oldName + "X";
		pet7.setName(newName);
		this.petService.savePet(pet7);

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionUpdatingPetsWithTheSameName() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		Pet pet = new Pet();
		pet.setName("wario");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		pet.setGenre("male");

		owner6.addPet(pet);
		
		Pet anotherPet = new Pet();		
		anotherPet.setName("waluigi");
		anotherPet.setGenre("male");
		anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
		owner6.addPet(anotherPet);
		
		try {
			petService.savePet(pet);
			petService.savePet(anotherPet);
		} catch (DuplicatedPetNameException e) {
			// The pets already exists!
			e.printStackTrace();
		}				
		
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			anotherPet.setName("wario");
			petService.savePet(anotherPet);
		});		
	}

	@Test
	@Transactional
	public void shouldAddNewVisitForPet() {
		Pet pet7 = this.petService.findPetById(7);
		int found = pet7.getVisits().size();
		Visit visit = new Visit();
		pet7.addVisit(visit);
		visit.setDescription("test");
		this.petService.saveVisit(visit);
            try {
                this.petService.savePet(pet7);
            } catch (DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
		assertThat(visit.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldFindVisitsByPetId() throws Exception {
		Collection<Visit> visits = this.petService.findVisitsByPetId(8);
		assertThat(visits.size()).isEqualTo(2);
		Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
		assertThat(visitArr[0].getPet()).isNotNull();
		assertThat(visitArr[0].getDate()).isNotNull();
		assertThat(visitArr[0].getPet().getId()).isEqualTo(8);
	}
	

	//-------------------------------------------------------------------------------HU.6------------------------------------------------------------------------------------------
		//Positive Case
		@Test 
		@DirtiesContext
		void shouldInsertPetByAnimalShelter() throws  DuplicatedPetNameException {
			//We get an animalshelter from the repository
		List<Animalshelter> animalshelters=(List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter animalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 1);
		Integer oldtam=animalshelter.getOwner().getPets().size();

		//We create a pet, add it to the animalshelter and save it
		Pet pet= new Pet();
		pet.setName("Peach");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		pet.setGenre("female");
		pet.changeOwner(animalshelter.getOwner());
		this.petService.savePet(pet);
		animalshelter.getOwner().addPet(pet);
		this.animalshelterService.save(animalshelter);
		
		//We recover the same animalshelter and try if it save it correctly
		List<Animalshelter> Newanimalshelters=(List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter Newanimalshelter = EntityUtils.getById(Newanimalshelters, Animalshelter.class, 1);
		
		Integer newtam= Newanimalshelter.getOwner().getPets().size();

	     assertThat(oldtam).isLessThan(newtam);
	      this.animalshelterRepository.deleteById(Newanimalshelter.getId());
		
		}

		//Negative Case
		@Test
		@DirtiesContext
		void shouldnotInsertPetByAnimalShelter() throws  DuplicatedPetNameException  {
			//We get an animalshelter from the repository
		List<Animalshelter> animalshelters=(List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter animalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 1);

		
		//We create a pet, add it to the animalshelter and try to save it
		Pet pet= new Pet();
		pet.setName("Dayi");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.changeOwner(animalshelter.getOwner());
		 assertThrows(ConstraintViolationException.class, () -> {
	       this.petService.save(pet);
	   });
	   
		}
		
	//-------------------------------------------------------------------------------HU.1------------------------------------------------------------------------------------------

	@Test
	@DirtiesContext
	void shouldFindPetsToAdopt() throws Exception {
		Collection<Pet> toAdopt = this.petService.findAdoptionPets();
	Integer size=this.animalshelterService.findAnimalshelters().stream().map(x->x.getOwner()).map(x->x.getPets()).distinct().collect(Collectors.toList()).size(); 
		assertThat(toAdopt.size()).isEqualTo(size);
	}
	
	@Test
	@Transactional
	void shouldNotFindPetsToAdopt() throws Exception{
		Collection<Owner> owners = this.ownerService.findAllOwnerCollection();
		Collection<Owner> shelters = this.ownerService.findOwnerByLastName("Shelter");
		List<Pet> pets = new ArrayList<Pet>();
		if(owners.removeAll(shelters)) {
			for(Owner o: owners) {
				pets.addAll(o.getPets());
			}
		}
		for(Pet p:pets) {
			assertThat(p.getId()).isNotEqualTo(14);
			assertThat(p.getId()).isNotEqualTo(15);
		}
		Collection<Pet> toAdopt = this.petService.findAdoptionPets();
		for(Pet p:pets) {
			for(Pet pet:toAdopt) {
				assertThat(p).isNotEqualTo(pet);
			}
		}

	}

}
