package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("universityService")
public class UniversityService {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository universityRepository;
    
    public University findByAbbreviation(String abbreviation) {
        
        return universityRepository.findByAbbreviation(abbreviation);
    }
}
