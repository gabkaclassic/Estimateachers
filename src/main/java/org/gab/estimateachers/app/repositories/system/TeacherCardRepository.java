package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.TeacherCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCardRepository extends CrudRepository<TeacherCard, Long> {

}
