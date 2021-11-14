package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("universityService")
public class UniversityService implements org.gab.estimateachers.app.services.Service<University> {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository universityRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public void save(University university) {
        
        universityRepository.save(university);
    }
    
    public void create(University university) {
    
        university.addPhoto(filesUtilities.registrationFile(null, RegistrationType.BUILDING));
        
        save(university);
    }
    
    public List<University> findAll() {
        
        return universityRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        universityRepository.deleteById(id);
    }
    
    public University findByAbbreviation(String abbreviation) {
        
        return universityRepository.findByAbbreviation(abbreviation);
    }
    
    public University findById(Long id) {
        
        return universityRepository.getOne(id);
    }
}
