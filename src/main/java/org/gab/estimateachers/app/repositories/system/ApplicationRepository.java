package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository<T extends Application> extends CrudRepository<T, Long>,
        JpaRepository<T, Long> {
    
}
