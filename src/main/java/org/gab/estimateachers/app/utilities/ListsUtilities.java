package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.services.ApplicationService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("listsUtilities")
public class ListsUtilities {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository universityRepository;
    
    @Autowired
    @Qualifier("dormitoryRepository")
    private DormitoryRepository dormitoryRepository;
    
    @Autowired
    @Qualifier("facultyRepository")
    private FacultyRepository facultyRepository;
    
    @Autowired
    @Qualifier("applicationService")
    private ApplicationService applicationService;
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    public List<User> getUsersList() {
    
        return userService.findALl();
    }
    
    public List<String> getGendersList() {
    
        return Arrays.stream(Genders.values())
                .map(Genders::toString)
                .collect(Collectors.toList());
    }
    
     public List<String> getUniversitiesAbbreviationsList() {
    
        return universityRepository.findAllAbbreviation();
    }
    
    public List<Application> getApplicationList() {
        
        return applicationService.findAll();
    }
    
    public List<String> getDormitoriesTitlesList(String abbreviationUniversity) {
        
        return universityRepository.findByAbbreviation(abbreviationUniversity).getDormitories()
                .stream().map(Dormitory::getTitle).collect(Collectors.toList());
    }
    
    public List<String> getFacultiesTitlesList(String abbreviationUniversity) {
        
        return universityRepository.findByAbbreviation(abbreviationUniversity).getFaculties()
                .stream().map(Faculty::getTitle).collect(Collectors.toList());
    }
}
