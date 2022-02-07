package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.estimations.TeacherEstimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("teacherEstimationRepository")
public interface TeacherEstimationRepository extends CrudRepository<TeacherEstimation, Long>,
        JpaRepository<TeacherEstimation, Long> {
    
}
