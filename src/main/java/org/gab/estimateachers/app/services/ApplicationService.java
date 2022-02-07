package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.system.applications.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class ApplicationService<T extends Application, R extends ApplicationRepository<T>>
        implements org.gab.estimateachers.app.services.Service<T> {
    
    protected R applicationRepository;
    
    @Autowired
    @Qualifier("facultyService")
    protected FacultyService facultyService;
    
    @Autowired
    @Qualifier("dormitoryService")
    protected DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("universityService")
    protected UniversityService universityService;
    
    @Autowired
    @Qualifier("studentService")
    protected StudentService studentService;
    
    @Autowired
    @Qualifier("mailService")
    protected MailService mailService;
    
    public List<T> findAll() {
        
        return applicationRepository.findAll();
    }
    
    public T findById(Long id) {
        
        return applicationRepository.getOne(id);
    }
    
    public void save(T object) {
        
        applicationRepository.save(object);
    }
    
    public void deleteById(Long applicationId) {
        
        applicationRepository.deleteById(applicationId);
    }
    
    protected void setApplicationRepository(R repository) {
        
        applicationRepository = repository;
    }
    
    public abstract void reject(Long applicationId, String reason);
}
