package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.services.CreatingCardApplicationService;
import org.gab.estimateachers.app.services.RegistrationApplicationService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("listsUtilities")
public class ListsUtilities {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository universityRepository;  //Заменить на сервисы
    
    @Autowired
    @Qualifier("dormitoryRepository")
    private DormitoryRepository dormitoryRepository;
    
    @Autowired
    @Qualifier("facultyRepository")
    private FacultyRepository facultyRepository;
    
    @Autowired
    @Qualifier("creatingCardApplicationService")
    private CreatingCardApplicationService creatingCardApplicationService;
    
    @Autowired
    @Qualifier("registrationApplicationService")
    private RegistrationApplicationService registrationApplicationService;
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    public List<User> getUsersList() {
    
        return userService.findAll();
    }
    
    public List<String> getGendersList() {
    
        return Arrays.stream(Genders.values())
                .map(Genders::toString)
                .collect(Collectors.toList());
    }
    
     public List<String> getUniversitiesAbbreviationsList() {
    
        return universityRepository.findAllAbbreviation();
    }
    
    public List<String> getDormitoriesTitlesList(University university) {
        
        return universityRepository.getOne(university.getId()).getDormitories()
                .stream().map(Dormitory::getTitle).collect(Collectors.toList());
    }
    
    public List<String> getFacultiesTitlesList(University university) {
        
        return universityRepository.getOne(university.getId()).getFaculties()
                .stream().map(Faculty::getTitle).collect(Collectors.toList());
    }
    
    public List<User> getFilteredUsersList(String login) {
        
        return userService.findByLogin(login);
    }
    
    public List<CreatingCardApplication> getCreatingCardApplicationList() {
        
        return creatingCardApplicationService.findAll();
    }
    
    public List<RegistrationApplication> getRegistrationApplicationList() {
        
        return registrationApplicationService.findAll();
    }
    
    public List<String> convertToTitlesList(Set<? extends Card> cards) {
        
        return cards.stream().map(Card::getTitle).collect(Collectors.toList());
    }
    
    public List<String> getAllFacultiesTitlesList() {
        
        return facultyRepository.findAllTitle();
    }
}
