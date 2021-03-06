/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.service.exceptions.PetNotRegistredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class AppointmentService {

	private final AppointmentRepository	appointmentRepository;

	private PetRepository				petRepository;


	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository, final PetRepository petRepository) {
		this.appointmentRepository = appointmentRepository;
		this.petRepository = petRepository;

	}

	@Transactional(rollbackFor = PetNotRegistredException.class)
	public void saveAppointment(final Appointment appointment) throws DataAccessException, PetNotRegistredException {
		Collection<Pet> a = this.petRepository.findAll();
		if (a.stream().filter(x -> x.equals(appointment.getPet())).collect(Collectors.toList()).isEmpty()) {
			throw new PetNotRegistredException();
		} else {
			this.appointmentRepository.save(appointment);
		}
	}

	@Transactional
	public Iterable<Appointment> findAll() {
		return this.appointmentRepository.findAll();
	}

	//Historia de usuario 17: muestra todas las citas del veterinario loggeado y tambien las citas futuras
	@Transactional
	public Iterable<Appointment> findAllByVet(final int vetId) throws DataAccessException {
		return this.appointmentRepository.findAllByVet(vetId);
	}

	@Transactional(readOnly = true)
	public Appointment findOneById(final int appointmentId) throws DataAccessException {
		return this.appointmentRepository.findOneById(appointmentId);
	}

}
