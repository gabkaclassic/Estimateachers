package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.StudentRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("studentService")
public class StudentService {
    
    @Autowired
    @Qualifier("studentRepository")
    private StudentRepository studentRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public void createStudent(String firstName,
                              String lastName,
                              Integer age,
                              Genders gender,
                              MultipartFile file,
                              User user
                              ) {
    
        Student student = new Student(
                firstName,
                lastName,
                age,
                gender,
                filesUtilities.studentRegistrationFile(file),
                user
        );
        System.out.println(student.getFilename());
        studentRepository.save(student);
    }
}
