package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Teacher;
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
            value = "select title from teachers where approved = 't' order by title;",
            nativeQuery = true
    )
    List<String> getTitles();
    
    @Query(
            value = "select teacher from Teacher teacher where teacher.title in :titles"
    )
    List<Teacher> findByTitles(@Param("titles") Set<String> teachersTitles);
    
    @Query(
            value = "select t from Teacher t where t.approved = true order by title"
    )
    List<Teacher> findAllApproved();
    
    @Query(
            value = "select t from Teacher t where lower(t.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Teacher> findByTitlePattern(@Param("title") String pattern);
    
    @Query(
            value = "select t from Teacher t where t.id in :list"
    )
    List<Teacher> findByListId(@Param("list") Set<Long> teachersId);
    
    @Query(
            value = "select t from Teacher t where t.id in :list and lower(t.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Teacher> findByListIdAndPattern(@Param("list") Set<Long> teachersId, @Param("title") String pattern);
}
