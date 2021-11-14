package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("facultyService")
public class FacultyService implements org.gab.estimateachers.app.services.Service<Faculty> {
    
    @Autowired
    @Qualifier("facultyRepository")
    private FacultyRepository facultyRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public Faculty findById(Long id) {
        
        return facultyRepository.getOne(id);
    }
    
    public void save(Faculty faculty) {
        
        facultyRepository.save(faculty);
    }
    
    public List<Faculty> findAll() {
        
        return facultyRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        facultyRepository.deleteById(id);
    }
    
    public Faculty findByTitle(String title) {
        
        return facultyRepository.findByTitle(title);
    }
}
