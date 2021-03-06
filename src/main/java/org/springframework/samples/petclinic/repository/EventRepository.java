
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

	@Override
	Collection<Event> findAll() throws DataAccessException;

	Event findById(int id) throws DataAccessException;

}
