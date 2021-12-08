package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    
    public University create(String universityTitle) {
    
        University university = new University(universityTitle);
        university.addPhoto(filesUtilities.registrationFile(null, RegistrationType.BUILDING));
        
        save(university);
        
        return university;
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
    
    public List<University> findByAbbreviations(Set<String> abbreviations) {
       
        return universityRepository.findAllByAbbreviation(abbreviations);
    }
    
    public University findById(Long id) {
        
        return universityRepository.getOne(id);
    }
}