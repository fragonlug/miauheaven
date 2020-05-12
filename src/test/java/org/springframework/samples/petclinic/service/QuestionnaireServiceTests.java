
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.service.exceptions.UmbralInferiorException;
import org.springframework.samples.petclinic.service.exceptions.UnrelatedPetException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class QuestionnaireServiceTests {

	@Autowired
	protected VetService			vetService;

	@Autowired
	protected QuestionnaireService	questService;
	@Autowired
	protected AnimalshelterService	animalshelterService;


	@Test
	public void findAllByPetId() {
		Collection<Questionnaire> questionnaires = this.questService.findQuestionnaireByPetId(14);
		for (Questionnaire q : questionnaires) {
			Assertions.assertThat(q.getHorasLibres()).isNotNull();
			Assertions.assertThat(q.getVivienda()).isNotNull();
			Assertions.assertThat(q.getIngresos()).isNotNull();
			Assertions.assertThat(q.getConvivencia()).isNotNull();

		}
	}

	//HU.5
	//Positive Case
	@Test
	void shouldFindMyApplicationsByPet() {
		//We get an animalshelter from the repository
		List<Animalshelter> animalshelters = (List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter animalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 1);
		Pet pet = animalshelter.getOwner().getPets().get(0);
		List<Questionnaire> quests = (List<Questionnaire>) this.questService.findQuestionnaireByPetId(pet.getId());
		//We see if the collection of questionnaires is empty or not, in this case should not be empty by the initial data.
		Assertions.assertThat(quests.isEmpty()).isEqualTo(false);
	}

	//Negative
	@Test
	void shouldNotFindMyApplicationsByPet() {
		//We get an animalshelter from the repository
		List<Animalshelter> animalshelters = (List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter animalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 1);
		Animalshelter OtherAnimalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 2);
		Pet pet = animalshelter.getOwner().getPets().get(0);
		List<Questionnaire> quests;
//		try {
//		 this.questService.findMyQuestionnaireByPetId(OtherAnimalshelter.getId(), pet.getId());
//			//We see if the collection of questionnaires is empty or not, in this case should not be empty by the initial data.
//			//Assertions.assertThat(quests.isEmpty()).isEqualTo(true);
//		} catch (UnrelatedPetException e) {
//			Logger.getLogger(QuestionnaireServiceTests.class.getName()).log(Level.SEVERE, null, e);
//		}
		 assertThrows(UnrelatedPetException.class, () -> {
	            questService.findMyQuestionnaireByPetId(OtherAnimalshelter.getId(), pet.getId());
	        });
	}

}
