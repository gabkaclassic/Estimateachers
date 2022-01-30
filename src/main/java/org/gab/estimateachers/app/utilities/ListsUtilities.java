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
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("listsUtilities")
public class ListsUtilities {
    
    private static final List<String> GENDERS = Arrays.stream(Genders.values())
            .map(Genders::name)
            .collect(Collectors.toList());
    
    private static final String UNIVERSITY_LIST_NAME = "Universities";
    private static final String DORMITORY_LIST_NAME = "Dormitories";
    private static final String FACULTY_LIST_NAME = "Faculties";
    private static final String TEACHERS_LIST_NAME = "Teachers";
    private static final String UNKNOWN_LIST_NAME = "Unknown";
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("dormitoryService")
    private DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
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
    
        return universityService.findAllAbbreviationApproved();
    }
    
    public List<String> getDormitoriesTitlesList(University university) {
        
        return universityService.findById((university.getId())).getDormitories()
                .stream()
                .filter(Dormitory::getApproved)
                .map(Dormitory::getTitle)
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<String> getFacultiesTitlesList(University university) {
        
        return universityService.findById(university.getId()).getFaculties()
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
        
        return facultyService.findAllTitles().stream().sorted().collect(Collectors.toList());
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
    
    public void findAllApproved(String cardType, Model model) {
    
        List<Card> list;
        String cardsType;
        
        switch (CardType.valueOf(cardType)) {
            case UNIVERSITY -> {
                list = universityService.findAllApproved();
                cardsType = UNIVERSITY_LIST_NAME;
            }
            case DORMITORY -> {
                list = dormitoryService.findAllApproved();
                cardsType = DORMITORY_LIST_NAME;
            }
            case FACULTY -> {
                list = facultyService.findAllApproved();
                cardsType = FACULTY_LIST_NAME;
            }
            case TEACHER -> {
                list = teacherService.findAllApproved();
                cardsType = TEACHERS_LIST_NAME;
            }
            default -> {
                list = universityService.findAllApproved();
                list.addAll(dormitoryService.findAllApproved());
                list.addAll(facultyService.findAllApproved());
                list.addAll(teacherService.findAllApproved());
                cardsType = "All cards";
            }
        }
    
        model.addAttribute("numbers", Stream.iterate(1, n -> n+1).limit(list.size()).collect(Collectors.toList()));
        model.addAttribute("cardType", cardType);
        model.addAttribute("cards", list);
        model.addAttribute("listName", cardsType);
    }
    
    public void findByTitlePattern(String title, String cardType, Model model) {
        
        List<Card> list;
        String cardsType;
        
        switch (CardType.valueOf(cardType)) {
            case UNIVERSITY -> {
                list = universityService.findByTitlePattern(title);
                cardsType = UNIVERSITY_LIST_NAME;
            }
            case DORMITORY -> {
                list = dormitoryService.findByTitlePattern(title);
                cardsType = DORMITORY_LIST_NAME;
            }
            case FACULTY -> {
                list = facultyService.findByTitlePattern(title);
                cardsType = FACULTY_LIST_NAME;
            }
            case TEACHER -> {
                list = teacherService.findByTitlePattern(title);
                cardsType = TEACHERS_LIST_NAME;
            }
            default -> {
                list = universityService.findByTitlePattern(title);
                list.addAll(dormitoryService.findByTitlePattern(title));
                list.addAll(facultyService.findByTitlePattern(title));
                list.addAll(teacherService.findByTitlePattern(title));
                cardsType = "All cards";
            }
        }
    
        model.addAttribute("listName", cardsType.substring(0, 1).toUpperCase().concat(cardsType.substring(1)));
        model.addAttribute("cardType", cardType);
        model.addAttribute("numbers", Stream.iterate(1, n -> n + 1).limit(list.size()).collect(Collectors.toList()));
        model.addAttribute("title", title);
        model.addAttribute("cards", list);
    }
}
