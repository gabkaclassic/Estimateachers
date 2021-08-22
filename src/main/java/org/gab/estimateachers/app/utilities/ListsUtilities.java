package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("listsUtilities")
public class ListsUtilities {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository universityRepository;
    
    @Autowired
    @Qualifier("applicationRepository")
    private ApplicationRepository applicationRepository;
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    public List<User> getUsersList() {
    
        return this.userRepository.findAll();
    }
    
    public List<String> getGendersList() {
    
        return Arrays.stream(Genders.values())
                .map(Genders::toString)
                .collect(Collectors.toList());
    }
    
     public List<String> getUniversitiesAbbreviationsList() {
    
        List<String> universities = new ArrayList<>();
    
        for(University university: this.universityRepository.findAll())
            universities.add(university.getAbbreviation());
    
        universities = universities.stream()
                .sorted()
                .collect(Collectors.toList());
        
        return universities;
    }
    
    public List<Application> getApplicationList() {
        
        return applicationRepository.findAll();
    }
}
