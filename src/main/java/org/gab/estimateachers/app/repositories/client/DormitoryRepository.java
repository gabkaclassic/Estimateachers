package org.gab.estimateachers.app.repositories.client;

import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dormitoryRepository")
public interface DormitoryRepository extends CrudRepository<Dormitory, Long>,
        JpaRepository<Dormitory, Long> {
    
    Dormitory findByTitle(String title);
}
