package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.CreatingCardApplicationRepository;
import org.gab.estimateachers.entities.client.*;
import org.gab.estimateachers.entities.client.CardType;
import org.gab.estimateachers.entities.system.applications.CreatingCardApplication;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("creatingCardApplicationService")
public class CreatingCardApplicationService extends ApplicationService<CreatingCardApplication, CreatingCardApplicationRepository> {
    
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("creatingCardApplicationRepository")
    protected void setApplicationRepository(CreatingCardApplicationRepository repository) {
        
        super.setApplicationRepository(repository);
    }
    
    public void create(CardType type, Card card, User user, String date) {
        
        CreatingCardApplication application = new CreatingCardApplication(user.getOwner(), date, type, card.getId());
        applicationRepository.save(application);
    }
    
    public List<CreatingCardApplication> findAllNotViewed() {
        
        return applicationRepository.findAllNotViewed();
    }
    
    public void approve(Long applicationId) {
        
        CreatingCardApplication application = findById(applicationId);
        mailService.applyCard(application.getStudent().getAccount());
        
        switch (application.getCardType()) {
        
            case UNIVERSITY -> universityService.approve(application.getCardId());
            case DORMITORY -> dormitoryService.approve(application.getCardId());
            case TEACHER -> teacherService.approve(application.getCardId());
            case FACULTY -> facultyService.approve(application.getCardId());
        }
        
        applicationRepository.deleteById(applicationId);
    }
    
    public void reject(Long applicationId, String reason) {
    
        CreatingCardApplication application = findById(applicationId);
        mailService.rejectCard(application.getStudent().getAccount(), reason);
        switch (application.getCardType()) {
    
            case UNIVERSITY -> universityService.deleteById(application.getCardId());
            case DORMITORY -> dormitoryService.deleteById(application.getCardId());
            case TEACHER -> teacherService.deleteById(application.getCardId());
            case FACULTY -> facultyService.deleteById(application.getCardId());
        }
    
        applicationRepository.deleteById(applicationId);
    }
}
