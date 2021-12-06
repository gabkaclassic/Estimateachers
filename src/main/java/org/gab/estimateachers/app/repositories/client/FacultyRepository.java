package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("facultyRepository")
public interface FacultyRepository extends CrudRepository<Faculty, Long>,
        JpaRepository<Faculty, Long> {
    
    List<Faculty> findByTitle(String title);
    
    @Query(
            value = "select faculty from Faculty faculty where faculty.title in :titles"
    )
    List<Faculty> findAllByTitle(@Param("titles") Set<String> facultiesTitles);
    
    @Query(
            value = "select title from faculties;",
            nativeQuery = true
    )
    List<String> findAllTitle();
    
    Faculty findByTitleAndUniversity(String title, University university);
}
