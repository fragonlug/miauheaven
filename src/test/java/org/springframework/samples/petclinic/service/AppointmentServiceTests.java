
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.exceptions.PetNotRegistredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentServiceTests {

	@Autowired
	private AnimalshelterService	animalshelterService;

	@Autowired
	private PetService				petService;

	@Autowired
	private VetService				vetService;

	@Autowired
	private AppointmentService		appointmentService;
	
	@Test
	@Transactional
	public void testSaveAppointment() {
		Vet vet = this.vetService.findVetById(1);
		Pet pet = this.petService.findPetById(1);
		Owner owner = pet.getOwner();
		
		Collection<Appointment> appointments1 = (Collection<Appointment>) this.appointmentService.findAll();
		Integer cantidadInicial = appointments1.size();
		Appointment nueva = new Appointment();
		
		
		nueva.setDate(LocalDate.now());
		nueva.setCause("Causa");
		nueva.setUrgent(false);
		nueva.setOwner(owner);
		nueva.setPet(pet);
		nueva.setVet(vet);
		
		try {
			this.appointmentService.saveAppointment(nueva);
		} catch (DataAccessException | PetNotRegistredException e) {
			e.printStackTrace();
		}
		
		Collection<Appointment> appointments2 = (Collection<Appointment>) this.appointmentService.findAll();
		Integer cantidadFinal = appointments2.size();
		
		Assertions.assertThat(cantidadInicial == cantidadFinal - 1);
		
	}


	@Test
	public void findAllByVet() {
		Iterable<Appointment> appointment = this.appointmentService.findAllByVet(1);
		for (Appointment a : appointment) {
			Assertions.assertThat(a.getCause()).isNotEmpty();
			Assertions.assertThat(a.getDate()).isNotNull();
			Assertions.assertThat(a.getUrgent()).isNotNull();

		}
	}

	// ---------------------------------------------------------------- HU.14 ----------------------------------------------------------------------------------------------------

	@Test //+
	@Transactional
	public void animalShelterShouldCreateAppointment() {
		//Se crea el appointment sin problema
		Owner animalshelter = this.animalshelterService.findOwnerByUsername("shelter1");
		Pet pet = this.petService.findPetById(14);
		Vet vet = this.vetService.findVetById(1);
		Appointment appointment = new Appointment();
		appointment.setOwner(animalshelter);
		appointment.setPet(pet);
		appointment.setVet(vet);
		appointment.setVet_id(1);
		appointment.setUrgent(false);
		appointment.setCause("Prueba");
		appointment.setDate(LocalDate.now());

		try {
			this.appointmentService.saveAppointment(appointment);
		} catch (PetNotRegistredException e) {
			e.printStackTrace();
		}

		for (Appointment v : this.appointmentService.findAllByVet(1)) {
			if (v.equals(appointment)) {
				Assertions.assertThat(v).isEqualTo(appointment);
			}
		}

	}

	@Test //-
	@Transactional
	public void animalShelterShouldNotCreateAppointment() throws PetNotRegistredException {
		//Vemos que ninguna mascota corresponde a la que se le asocia
		Owner animalshelter = this.animalshelterService.findOwnerByUsername("shelter1");
		Pet pet = new Pet();
		pet.setName("Flippers");
		pet.setBirthDate(LocalDate.of(2018, 04, 12));
		pet.setGenre("male");
		PetType petType = new PetType();
		petType.setName("cat");
		pet.setType(petType);
		pet.setVisits(new HashSet<>());
		Vet vet = this.vetService.findVetById(1);
		Appointment appointment = new Appointment();
		appointment.setOwner(animalshelter);
		appointment.setPet(pet);
		appointment.setVet(vet);
		appointment.setVet_id(1);
		appointment.setUrgent(false);
		appointment.setCause("Prueba");
		appointment.setDate(LocalDate.now());
		try {
			this.appointmentService.saveAppointment(appointment);
		} catch (PetNotRegistredException e) {
			e.printStackTrace();
		}

	}

	// ---------------------------------------------------------------- HU.16 ----------------------------------------------------------------------------------------------------

	@Test //+
	@Transactional
	public void animalShelterShouldCreateUrgentAppointment() {
		//Creamos con normalidad la cita siendo urgente
		Owner animalshelter = this.animalshelterService.findOwnerByUsername("shelter1");
		Pet pet = this.petService.findPetById(14);
		Vet vet = this.vetService.findVetById(1);
		Appointment appointment = new Appointment();
		appointment.setOwner(animalshelter);
		appointment.setPet(pet);
		appointment.setVet(vet);
		appointment.setVet_id(1);
		appointment.setUrgent(true);
		appointment.setCause("Prueba");
		appointment.setDate(LocalDate.now());

		try {
			this.appointmentService.saveAppointment(appointment);
		} catch (PetNotRegistredException e) {
			e.printStackTrace();
		}

		for (Appointment v : this.appointmentService.findAllByVet(1)) {
			if (v.equals(appointment)) {
				Assertions.assertThat(v).isEqualTo(appointment);
			}
		}

	}

	@Test //-
	@Transactional
	public void animalShelterShouldNotCreateUrgentAppointment() {
		//Intentamos guardar un appointment sin especificar si es urgente o no
		Owner animalshelter = this.animalshelterService.findOwnerByUsername("shelter1");
		Pet pet = this.petService.findPetById(14);
		Vet vet = this.vetService.findVetById(1);
		Appointment appointment = new Appointment();
		appointment.setOwner(animalshelter);
		appointment.setPet(pet);
		appointment.setVet(vet);
		appointment.setVet_id(1);
		appointment.setCause("Prueba");
		appointment.setDate(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () -> {
			this.appointmentService.saveAppointment(appointment);
		});
	}
}
