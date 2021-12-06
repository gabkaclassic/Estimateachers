package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("universityRepository")
public interface UniversityRepository extends CrudRepository<University, Long>,
        JpaRepository<University, Long> {
    
    University findByAbbreviation(String abbreviationUniversity);
    
    @Query(
            value = "select abbreviation from universities;",
            nativeQuery = true
    )
    List<String> findAllAbbreviation();
    
    @Query(
            value = "select university from University university where university.abbreviation in :abbreviations"
    )
    List<University> findAllByAbbreviation(@Param("abbreviations") Set<String> universitiesAbbreviations);
}
