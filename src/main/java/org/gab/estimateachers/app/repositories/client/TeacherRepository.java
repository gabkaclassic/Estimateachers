package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}
