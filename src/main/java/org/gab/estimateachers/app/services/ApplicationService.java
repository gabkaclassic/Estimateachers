package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    @Qualifier("applicationRepository")
    private ApplicationRepository applicationRepository;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
    @Autowired
    @Qualifier("dormitoryService")
    private DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;
    
    public List<Application> findAll() {
        
        return applicationRepository.findAll();
    }
    
    public Application findById(Long id) {
        
        return applicationRepository.getOne(id);
    }
    
    public void apply(Long applicationId, String facultyTitle, String dormitoryTitle, Integer course) {
    
        Application application = findById(applicationId);
        Faculty faculty = facultyService.findByTitle(facultyTitle);
        Dormitory dormitory = dormitoryService.findByTitle(dormitoryTitle);
        Student student = application.getStudent();
    
        student.setFaculty(faculty);
        student.setDormitory(dormitory);
        student.setCourse(course);
        student.setUniversity(faculty.getUniversity());
        student.getAccount().apply();
        
        applicationRepository.delete(application);
        studentService.save(student);
    }
    
    public void deleteById(Long applicationId) {
        
        applicationRepository.deleteById(applicationId);
    }
}
