package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.StudentRepository;
import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.users.Genders;
import org.gab.estimateachers.entities.system.applications.RegistrationApplication;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service("studentService")
public class StudentService implements org.gab.estimateachers.app.services.Service<Student> {
    
    @Autowired
    @Qualifier("studentRepository")
    private StudentRepository studentRepository;
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    @Autowired
    @Qualifier("applicationRepository")
    private ApplicationRepository<RegistrationApplication> applicationRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("mailService")
    protected MailService mailService;
    
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
                              String patronymic,
                              Genders gender,
                              MultipartFile profilePhoto,
                              MultipartFile cardPhoto,
                              String date,
                              User user) {
        
        user.setActivationCode(UUID.randomUUID().toString());
        user.setFilename(filesUtilities.registrationFile(profilePhoto, RegistrationType.PEOPLE));
        Student student = new Student(
                firstName,
                lastName,
                patronymic,
                gender,
                user
        );
        user.setOwner(student);
        userRepository.save(user);
    
        mailService.sendConfirmEmail(user);
        
        RegistrationApplication application = new RegistrationApplication(
                student,
                date,
                filesUtilities.registrationFile(cardPhoto, RegistrationType.OTHER)
        );
        
        applicationRepository.save(application);
    }
}
