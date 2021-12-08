package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("registrationApplicationService")
public class RegistrationApplicationService extends ApplicationService<RegistrationApplication> {
    
    @Autowired
    @Qualifier("registrationApplicationRepository")
    protected void setApplicationRepository(ApplicationRepository<RegistrationApplication> repository) {
        
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
    
        studentService.save(student);
        applicationRepository.delete(application);
        
    }
}
