package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("creatingCardApplicationRepository")
public interface CreatingCardApplicationRepository extends ApplicationRepository<CreatingCardApplication> {
    
    @Query(
            value = "select * from creating_card_applications where viewed = 'f'",
            nativeQuery = true
    )
    List<CreatingCardApplication> findAllNotViewed();
}
