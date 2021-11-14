package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.StudentRepository;
import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("studentService")
public class StudentService implements org.gab.estimateachers.app.services.Service<Student> {
    
    @Autowired
    @Qualifier("studentRepository")
    private StudentRepository studentRepository;
    
    @Autowired
    @Qualifier("applicationRepository")
    private ApplicationRepository applicationRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public Student findById(Long id) {
        
        return studentRepository.getOne(id);
    }
    
    public void save(Student student) {
        
        studentRepository.save(student);
    }
    
    public List<Student> findAll() {
        
        return studentRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        studentRepository.deleteById(id);
    }
    
    public void sendApplication(String firstName,
                              String lastName,
                              Genders gender,
                              MultipartFile profilePhoto,
                              MultipartFile cardPhoto,
                              User user) {
    
        user.setFilename(filesUtilities.registrationFile(profilePhoto, RegistrationType.PEOPLE));
        
        Student student = new Student(
                firstName,
                lastName,
                gender,
                user
        );
    
        Application application = new Application(
                filesUtilities.registrationFile(cardPhoto, RegistrationType.OTHER),
                student
        );
        
        applicationRepository.save(application);
    }
}
