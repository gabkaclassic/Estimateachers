package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.system.estimations.DormitoryEstimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("dormitoryEstimationRepository")
public interface DormitoryEstimationRepository extends CrudRepository<DormitoryEstimation, Long>,
        JpaRepository<DormitoryEstimation, Long> {
    
}
