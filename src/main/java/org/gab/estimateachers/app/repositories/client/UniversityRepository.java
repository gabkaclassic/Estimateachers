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
public interface UniversityRepository<Long> extends CrudRepository<University, Long>,
        JpaRepository<University, Long> {
    
    University findByAbbreviation(String abbreviationUniversity);
    
    @Query(
            value = "select abbreviation from universities order by abbreviations;",
            nativeQuery = true
    )
    List<String> findAllAbbreviation();
    
    @Query(
            value = "select university from University university where university.abbreviation in :abbreviations"
    )
    List<University> findAllByAbbreviation(@Param("abbreviations") Set<String> universitiesAbbreviations);
    
    boolean existsByTitle(String title);
    
    @Query(
            value = "select abbreviation from universities where approved = 't' order by abbreviation;",
            nativeQuery = true
    )
    List<String> findAllAbbreviationApproved();
    
    @Query(value = "select u from University u where u.approved = true order by title")
    List<University> findAllApproved();
    
    @Query(
            value = "select u from University u where lower(u.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<University> findByTitlePattern(@Param("title") String pattern);
    
    @Query(
            value = "select u from University u where lower(u.abbreviation) like lower(concat('%', :title, '%')) order by title"
    )
    List<University> findByAbbreviationPattern(@Param("title") String pattern);
    
    @Query(
            value = "select u from University u where lower(u.abbreviation) like lower(concat('%', :title, '%')) or lower(u.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<University> findByPattern(@Param("title") String pattern);
    
    @Query(
            value = "select u from University u where u.id in :list"
    )
    List<University> findByListId(@Param("list") Set<Long> universitiesId);
    
    @Query(
            value = "select u from University u where u.id in :list " +
                    "and (lower(u.abbreviation) like lower(concat('%', :title, '%')) or lower(u.title) like lower(concat('%', :title, '%'))) order by title"
    )
    List<University> findByListIdAndPattern(@Param("list") Set<Long> universitiesId, @Param("title") String pattern);
}