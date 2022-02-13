package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("dormitoryRepository")
public interface DormitoryRepository extends CrudRepository<Dormitory, Long>,
        JpaRepository<Dormitory, Long> {
    
    Dormitory findByTitle(String title);
    
    boolean existsByTitle(String title);
    
    @Query(
            value = "select d from Dormitory d where d.approved = true order by title"
    )
    List<Dormitory> findAllApproved();
    
    @Query(
            value = "select d from Dormitory d where lower(d.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Dormitory> findByTitlePattern(@Param("title") String pattern);
    
    @Query(
            value = "select d from Dormitory d where d.id in :list"
    )
    List<Dormitory> findByListId(@Param("list") Set<Long> dormitoriesId);
    
    @Query(
            value = "select d from Dormitory d where d.id in :list and lower(d.title) like lower(concat('%', :title, '%')) order by title"
    )
    List<Dormitory> findByListIdAndPattern(@Param("list") Set<Long> dormitoriesId, @Param("title") String pattern);
}
