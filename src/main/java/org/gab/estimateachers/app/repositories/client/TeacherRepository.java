package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long>,
        JpaRepository<Teacher, Long> {
    
    boolean existsByTitle(String title);
    
    @Query(
            value = "select title from teachers where approved = 't';",
            nativeQuery = true
    )
    List<String> getTitles();
    
    @Query(
            value = "select teacher from Teacher teacher where teacher.title in :titles"
    )
    List<Teacher> findByTitles(@Param("titles") Set<String> teachersTitles);
    
    @Query(value = "SELECT t FROM Teacher t WHERE t.approved = true")
    List<Teacher> findAllApproved();
}
