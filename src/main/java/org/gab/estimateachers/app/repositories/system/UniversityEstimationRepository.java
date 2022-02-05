package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.estimations.UniversityEstimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("universityEstimationRepository")
public interface UniversityEstimationRepository extends CrudRepository<UniversityEstimation, Long>,
        JpaRepository<UniversityEstimation, Long> {
    
    
    
}
