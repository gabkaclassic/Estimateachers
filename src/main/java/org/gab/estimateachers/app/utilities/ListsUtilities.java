package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.entities.client.*;
import org.gab.estimateachers.entities.system.applications.CreatingCardApplication;
import org.gab.estimateachers.entities.system.applications.RegistrationApplication;
import org.gab.estimateachers.entities.system.applications.Request;
import org.gab.estimateachers.entities.system.users.Genders;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("listsUtilities")
public class ListsUtilities {
    
    private static final List<String> GENDERS = Arrays.stream(Genders.values())
            .map(Genders::name)
            .collect(Collectors.toList());
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository<Card, Number> universityRepository;  //Заменить на сервисы
    
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
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("requestService")
    private RequestService requestService;
    
    public List<User> getUsersList() {
    
        return userService.findAll();
    }
    
    public List<String> getGendersList() {
    
        return GENDERS;
    }
    
     public List<String> getUniversitiesAbbreviationsList() {
    
        return universityRepository.findAllAbbreviationApproved();
    }
    
    public List<String> getDormitoriesTitlesList(University university) {
        
        return universityRepository.getOne(university.getId()).getDormitories()
                .stream()
                .filter(Dormitory::getApproved)
                .map(Dormitory::getTitle)
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<String> getFacultiesTitlesList(University university) {
        
        return universityRepository.getOne(university.getId()).getFaculties()
                .stream()
                .filter(Faculty::getApproved)
                .map(Faculty::getTitle)
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<User> getFilteredUsersList(String login) {
        
        return userService.findByLoginPattern(login).stream().sorted().collect(Collectors.toList());
    }
    
    public List<CreatingCardApplication> getCreatingCardApplicationList() {
        
        return creatingCardApplicationService.findAllNotViewed().stream().sorted().collect(Collectors.toList());
    }
    
    public List<RegistrationApplication> getRegistrationApplicationList() {
        
        return registrationApplicationService.findAllNotViewed();
    }
    
    public List<String> convertToTitlesList(Set<? extends Card> cards) {
        
        return cards.stream().map(Card::getTitle).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
    }
    
    public List<String> getAllFacultiesTitlesList() {
        
        return facultyRepository.findAllTitle().stream().sorted().collect(Collectors.toList());
    }
    
    public Object getNumbers(Collection<?> list) {
       
       return Stream.iterate(1, n -> n+1).limit(list.size()).collect(Collectors.toList());
    }
    
    public List<String> getTeachersTitles() {
        
        return teacherService.getTitles().stream().sorted().collect(Collectors.toList());
    }
    
    public List<Request> getCardRequestList() {
        
        return requestService.findAllCard();
    }
    public List<Request> getServiceRequestList() {
        
        return requestService.findAllService();
    }
    
}
