package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("applicationRepository")
public interface ApplicationRepository extends CrudRepository<Application, Long>,
        JpaRepository<Application, Long> {
    
    
}
