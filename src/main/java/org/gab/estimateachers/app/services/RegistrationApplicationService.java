package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.RegistrationApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.applications.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("registrationApplicationService")
public class RegistrationApplicationService extends ApplicationService<RegistrationApplication, RegistrationApplicationRepository> {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;
    
    @Autowired
    @Qualifier("registrationApplicationRepository")
    protected void setApplicationRepository(RegistrationApplicationRepository repository) {
        
        super.setApplicationRepository(repository);
    }
    public void apply(Long applicationId, Long universityId, String facultyTitle, String dormitoryTitle, Integer course) {
    
        RegistrationApplication application = findById(applicationId);
        Dormitory dormitory = (Objects.isNull(dormitoryTitle)) ? null : dormitoryService.findByTitle(dormitoryTitle);
        University university = universityService.findById(universityId);
        Faculty faculty = facultyService.findByTitleAndUniversity(facultyTitle, university);
        Student student = application.getStudent();
        
        student.setFaculty(faculty);
        if(Objects.nonNull(dormitory))
            student.setDormitory(dormitory);
        student.setCourse(course);
        student.setUniversity(university);
        student.getAccount().apply();
        student.getAccount().setOwner(student);
        faculty.getTeachers().forEach(student::addTeacher);
    
        studentService.save(student);
        applicationRepository.delete(application);
        mailService.notifyAccessRegistration(student.getAccount());
    }
    
    public List<RegistrationApplication> findAllNotViewed() {
        
        return applicationRepository.findAllNotViewed();
    }
    
    public void reject(Long applicationId, String reason) {
        
        RegistrationApplication application = findById(applicationId);
        mailService.notifyRejectRegistration(application.getStudent().getAccount(), reason);
        userService.deleteById(application.getStudent().getAccount().getId());
        studentService.deleteById(application.getStudent().getId());
        deleteById(applicationId);
    }
}
