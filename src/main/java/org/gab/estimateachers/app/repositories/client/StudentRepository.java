package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

}
