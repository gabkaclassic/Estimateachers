package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.system.estimations.FacultyEstimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("facultyEstimationRepository")
public interface FacultyEstimationRepository extends CrudRepository<FacultyEstimation, Long>,
        JpaRepository<FacultyEstimation, Long> {
    
}
