package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.TeacherRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("teacherService")
public class TeacherService {
    
    @Autowired
    @Qualifier("teacherRepository")
    TeacherRepository teacherRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    public void save(Teacher teacher) {
        
        teacherRepository.save(teacher);
    }
    
    public void create(Teacher teacher) {
        
        teacher.setFilename(filesUtilities.registrationFile(null, RegistrationType.PEOPLE));
        
        save(teacher);
    }
}
