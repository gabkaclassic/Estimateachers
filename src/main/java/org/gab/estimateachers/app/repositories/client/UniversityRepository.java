package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("universityRepository")
public interface UniversityRepository extends CrudRepository<University, Long>,
        JpaRepository<University, Long> {

}
