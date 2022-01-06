package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("universityRepository")
public interface UniversityRepository<U extends Card, Long> extends CrudRepository<University, Long>,
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
    
    boolean existsByTitle(String title);
    
    @Query(
            value = "select abbreviation from universities where approved = 't';",
            nativeQuery = true
    )
    List<String> findAllAbbreviationApproved();
    
    @Query(value = "SELECT u FROM University u WHERE u.approved = true")
    List<University> findAllApproved();
    
    @Query(
            value = "select u from University u where lower(u.title) like lower(concat('%', :title, '%'))"
    )
    List<University> findByTitlePattern(@Param("title") String pattern);
}