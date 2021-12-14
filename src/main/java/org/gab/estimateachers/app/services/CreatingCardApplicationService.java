package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.repositories.system.CreatingCardApplicationRepository;
import org.gab.estimateachers.entities.client.*;
import org.gab.estimateachers.entities.system.CardType;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.User;
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
    
    public void create(CardType type, Card card, User user, String date) {
        
        CreatingCardApplication application = new CreatingCardApplication(user.getOwner(), date, type, card.getId());
        applicationRepository.save(application);
    }
    
}
