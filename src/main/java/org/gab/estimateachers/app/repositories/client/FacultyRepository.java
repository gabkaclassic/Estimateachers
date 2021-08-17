package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {

}
