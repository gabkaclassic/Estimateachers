package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("registrationApplicationService")
public class RegistrationApplicationService extends ApplicationService<RegistrationApplication> {
    
    @Autowired
    @Qualifier("registrationApplicationRepository")
    protected void setApplicationRepository(ApplicationRepository<RegistrationApplication> repository) {
        
        super.setApplicationRepository(repository);
    }
}
