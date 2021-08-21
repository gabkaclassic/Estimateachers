package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.client.University;
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

@Controller
public class ListsUtilities {
    
//    @Autowired
//    @Qualifier("universityRepository")
//    private UniversityRepository universityRepository;
//
//    @Autowired
//    @Qualifier("userRepository")
//    private UserRepository userRepository;
    
    List<User> getUsersList(UserRepository userRepository) {
    
        return userRepository.findAll();
    }
    
    List<String> getGendersList() {
    
        return Arrays.stream(Genders.values())
                .map(Genders::toString)
                .collect(Collectors.toList());
    }
    
     List<String> getUniversitiesAbbreviationsList(UniversityRepository universityRepository) {
    
        List<String> universities = new ArrayList<>();
    
        for(University university: universityRepository.findAll())
            universities.add(university.getAbbreviation());
    
        universities = universities.stream()
                .sorted()
                .collect(Collectors.toList());
        
        return universities;
    }
    
}
