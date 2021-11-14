package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.TeacherRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherService implements org.gab.estimateachers.app.services.Service<Teacher> {
    
    @Autowired
    @Qualifier("teacherRepository")
    TeacherRepository teacherRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public Teacher findById(Long id) {
        
        return teacherRepository.getOne(id);
    }
    
    public void save(Teacher teacher) {
        
        teacherRepository.save(teacher);
    }
    
    public void create(Teacher teacher) {
        
        teacher.addPhoto(filesUtilities.registrationFile(null, RegistrationType.PEOPLE));
        
        save(teacher);
    }
    
    public List<Teacher> findAll() {
        
        return teacherRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        teacherRepository.deleteById(id);
    }
}
