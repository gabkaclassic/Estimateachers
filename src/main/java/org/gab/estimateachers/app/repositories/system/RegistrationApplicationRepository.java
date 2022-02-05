package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.applications.RegistrationApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("registrationApplicationRepository")
public interface RegistrationApplicationRepository extends ApplicationRepository<RegistrationApplication>{
    
    @Query(
            value = "select ra from RegistrationApplication ra where ra.viewed = 'f'"
    )
    List<RegistrationApplication> findAllNotViewed();
}
