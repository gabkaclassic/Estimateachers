package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.applications.CreatingCardApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("creatingCardApplicationRepository")
public interface CreatingCardApplicationRepository extends ApplicationRepository<CreatingCardApplication> {
    
    @Query(
            value = "select cca from CreatingCardApplication cca where cca.viewed = 'f'"
    )
    List<CreatingCardApplication> findAllNotViewed();
}
