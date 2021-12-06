package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
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
    public void apply(Long applicationId, String facultyTitle, String dormitoryTitle, Integer course) {
        
        RegistrationApplication application = findById(applicationId);
        Faculty faculty = facultyService.findByTitleAndUniversity(facultyTitle, university);
        Dormitory dormitory = dormitoryService.findByTitle(dormitoryTitle);
        Student student = application.getStudent();
        
        student.setFaculty(faculty);
        student.setDormitory(dormitory);
        student.setCourse(course);
        student.setUniversity(faculty.getUniversity());
        student.getAccount().apply();
    
        studentService.save(student);
        applicationRepository.delete(application);
        
    }
}
