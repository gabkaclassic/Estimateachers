package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("facultyRepository")
public interface FacultyRepository extends CrudRepository<Faculty, Long>,
        JpaRepository<Faculty, Long> {
    
    Faculty findByTitle(String title);
}
