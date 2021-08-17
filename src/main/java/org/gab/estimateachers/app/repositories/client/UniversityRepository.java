package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends CrudRepository<University, Long> {

}
