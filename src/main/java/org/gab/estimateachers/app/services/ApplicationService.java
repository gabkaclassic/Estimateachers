package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class ApplicationService<T extends Application> implements org.gab.estimateachers.app.services.Service<T> {
    

    protected ApplicationRepository<T> applicationRepository;
    
    @Autowired
    @Qualifier("facultyService")
    protected FacultyService facultyService;
    
    @Autowired
    @Qualifier("dormitoryService")
    protected DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("studentService")
    protected StudentService studentService;
    
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
    
    protected void setApplicationRepository(ApplicationRepository<T> repository) {
        
        applicationRepository = repository;
    }
    
}
