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
            value = "select faculty from Faculty faculty where faculty.title in :titles order by title"
    )
    List<Faculty> findAllByTitle(@Param("titles") Set<String> facultiesTitles);
    
    @Query(
            value = "select title from faculties where approved = 't';",
            nativeQuery = true
    )
    List<String> findAllTitle();
    
    Faculty findByTitleAndUniversity(String title, University university);
    
    boolean existsByTitle(String facultyTitle);
    
    @Query(
            value = "select f from Faculty f where f.approved = true"
    )
    List<Faculty> findAllApproved();
    
    @Query(
            value = "select f from Faculty f where lower(f.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Faculty> findByTitlePattern(@Param("title") String pattern);
    
    @Query(
            value = "select f from Faculty f where f.id in :list"
    )
    List<Faculty> findByListId(@Param("list") Set<Long> facultiesId);
    
    @Query(
            value = "select f from Faculty f where f.id in :list and lower(f.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Faculty> findByListIdAndPattern(@Param("list") Set<Long> facultiesId, @Param("title") String pattern);
}
