package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.entities.client.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("facultyService")
public class FacultyService {
    
    @Autowired
    @Qualifier("facultyRepository")
    private FacultyRepository facultyRepository;
    
    public void save(Faculty faculty) {
        
        facultyRepository.save(faculty);
    }
    
    public Faculty findByTitle(String title) {
        
        return facultyRepository.findByTitle(title);
    }
    
}
