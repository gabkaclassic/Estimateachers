package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("applicationRepository")
public interface ApplicationRepository extends CrudRepository<Application, Long>,
        JpaRepository<Application, Long> {
    
}
