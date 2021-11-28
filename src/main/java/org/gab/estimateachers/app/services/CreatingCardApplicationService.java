package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.repositories.system.CreatingCardApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("creatingCardApplicationService")
public class CreatingCardApplicationService extends ApplicationService<CreatingCardApplication> {
    
    @Autowired
    @Qualifier("creatingCardApplicationRepository")
    protected void setApplicationRepository(ApplicationRepository<CreatingCardApplication> repository) {
        
        super.setApplicationRepository(repository);
    }
}
