package org.gab.estimateachers.app.controllers.cards;

import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.CardsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.*;
import org.gab.estimateachers.entities.client.CardType;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/cards")
public class CardsController {
    
    @Autowired
    @Qualifier("dormitoryService")
    private DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
    @Autowired
    @Qualifier("creatingCardApplicationService")
    private CreatingCardApplicationService creatingCardApplicationService;
    
    @Autowired
    @Qualifier("usersUtilities")
    private UsersUtilities usersUtilities;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("cardsUtilities")
    private CardsUtilities cardsUtilities;
    
    @GetMapping("/list/{cardsType}")
    public String cardsList(@AuthenticationPrincipal User user,
                            @PathVariable("cardsType") String cardsType,
                            Model model) {
        
        model.addAttribute("listName", cardsType.substring(0, 1).toUpperCase().concat(cardsType.substring(1)));
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        String cardType = (cardsType.equals("teachers")) ? cardsType.substring(0, cardsType.length()-1) : cardsType.replace("ies", "y");
        List<? extends Card> list;
        
        switch (cardsType) {
            case "universities" -> list = universityService.findAllApproved();
            case "dormitories" -> list = dormitoryService.findAllApproved();
            case "faculties" -> list = facultyService.findAllApproved();
            case "teachers" -> list = teacherService.findAllApproved();
            default -> list = Collections.emptyList();
        }
        
        model.addAttribute("numbers", Stream.iterate(1, n -> n+1).limit(list.size()).collect(Collectors.toList()));
        model.addAttribute("cardType", cardType);
        model.addAttribute("cards", list);
        
        return "/cards_list";
    }
    
    @PostMapping("/search/title")
    public String findByTitle(@AuthenticationPrincipal User user,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam("cardsType") String cardType,
                              Model model) {
    
        String cardsType = (cardType.equals("teacher")) ? cardType.concat("s") : cardType.replace("y", "ies");
        List<? extends Card> list = Collections.emptyList();
        
        if(Objects.nonNull(title) && !title.isEmpty()) {
        
            switch (cardsType) {
                case "universities" -> list = universityService.findByTitlePattern(title);
                case "dormitories" -> list = dormitoryService.findByTitlePattern(title);
                case "faculties" -> list = facultyService.findByTitlePattern(title);
                case "teachers" -> list = teacherService.findByTitlePattern(title);
                default -> {}
            }
        
            model.addAttribute("listName", cardsType.substring(0, 1).toUpperCase().concat(cardsType.substring(1)));
            model.addAttribute("cardType", cardType);
            model.addAttribute("numbers", Stream.iterate(1, n -> n + 1).limit(list.size()).collect(Collectors.toList()));
            model.addAttribute("title", title);
            model.addAttribute("cards", list);
        }
        else
            return "redirect:/cards/list/".concat(cardsType);
    
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
    
        return "/cards_list";
    }
    
    @PostMapping("/estimation/university")
    public String estimationUniversity(@AuthenticationPrincipal User user,
                                       @RequestParam(value = "priceRating", required = false) Integer priceRating,
                                       @RequestParam(value = "complexityRating", required = false) Integer complexityRating,
                                       @RequestParam(value = "utilityRating", required = false) Integer utilityRating,
                                       @RequestParam("cardId") Long universityId,
                                       Model model) {
        
        universityService.estimation(universityId, user, priceRating, complexityRating, utilityRating);
        
        return getCard(user, universityId, "university", model);
    }
    
    @PostMapping("/estimation/faculty")
    public String estimationFaculty(@AuthenticationPrincipal User user,
                                       @RequestParam(value = "priceRating", required = false) Integer priceRating,
                                       @RequestParam(value = "educationRating", required = false) Integer educationRating,
                                       @RequestParam("cardId") Long facultyId,
                                       Model model) {
        
        facultyService.estimation(facultyId, user, priceRating, educationRating);
        
        return getCard(user, facultyId, "faculty", model);
    }
    
    @PostMapping("/estimation/dormitory")
    public String estimationDormitory(@AuthenticationPrincipal User user,
                                    @RequestParam(value = "cleaningRating", required = false) Integer cleaningRating,
                                    @RequestParam(value = "roommatesRating", required = false) Integer roommatesRating,
                                    @RequestParam(value = "capacityRating", required = false) Integer capacityRating,
                                    @RequestParam("cardId") Long dormitoryId,
                                    Model model) {
        
        dormitoryService.estimation(dormitoryId, user, cleaningRating, roommatesRating, capacityRating);
        
        return getCard(user, dormitoryId, "dormitory", model);
    }
    
    @PostMapping("/estimation/teacher")
    public String estimationTeacher(@AuthenticationPrincipal User user,
                                    @RequestParam(value = "freebiesRating", required = false) Integer freebiesRating,
                                    @RequestParam(value = "exactingRating", required = false) Integer exactingRating,
                                    @RequestParam(value = "severityRating", required = false) Integer severityRating,
                                    @RequestParam("cardId") Long facultyId,
                                    Model model) {
        
        teacherService.estimation(facultyId, user, freebiesRating, exactingRating, severityRating);
        
        return getCard(user, facultyId, "teacher", model);
    }
    
    @GetMapping("/add")
    public String createCard(Model model) {
        
        model.addAttribute("universities", listUtilities.getUniversitiesAbbreviationsList());
        model.addAttribute("faculties", listUtilities.getAllFacultiesTitlesList());
        model.addAttribute("teachers", listUtilities.getTeachersTitles());
        
        return "/add_card_menu";
    }
    
    @PostMapping("/add/university")
    public String addUniversity(@AuthenticationPrincipal User user,
                                @RequestParam("title") String universityTitle,
                                @RequestParam(value = "bachelor", required = false) Boolean bachelor,
                                @RequestParam(value = "magistracy", required = false) Boolean magistracy,
                                @RequestParam(value = "specialty", required = false) Boolean specialty,
                                @RequestParam("date") String dateSending,
                                @RequestParam("files") Set<MultipartFile> files,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks, universityService);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
        
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            universityService.create(universityTitle, bachelor, magistracy, specialty, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.UNIVERSITY,
                    universityService.create(universityTitle, bachelor, magistracy, specialty, files, user.isAdmin()),
                    user,
                    dateSending
            );
        
        return createCard(model);
    }
    
    @PostMapping("/add/dormitory")
    public String addDormitory(@AuthenticationPrincipal User user,
                               @RequestParam("title") String dormitoryTitle,
                               @RequestParam("university") String universityAbbreviation,
                               @RequestParam("date") String dateSending,
                               @RequestParam("files") Set<MultipartFile> files,
                               Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks, dormitoryService);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
            
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation), files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.DORMITORY,
                    dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation), files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        model.addAttribute("toast", true);
    
        return createCard(model);
    }
    
    @PostMapping("/add/faculty")
    public String addFaculty(@AuthenticationPrincipal User user,
                             @RequestParam("title") String facultyTitle,
                             @RequestParam("university") String universityAbbreviation,
                             @RequestParam("date") String dateSending,
                             @RequestParam("teachers") Set<String> teachersTitles,
                             @RequestParam("files") Set<MultipartFile> files,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks, facultyService);
        
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
        
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            facultyService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation), teachersTitles, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.FACULTY,
                    facultyService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation), teachersTitles, files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        model.addAttribute("toast", true);
    
        return createCard(model);
    }
    
    @PostMapping("/add/teacher")
    public String addTeacher(@AuthenticationPrincipal User user,
                             @RequestParam("firstName") String firstname,
                             @RequestParam("lastName") String lastname,
                             @RequestParam(value = "patronymic", required = false) String patronymic,
                             @RequestParam("universities") Set<String> universitiesAbbreviation,
                             @RequestParam("faculties") Set<String> facultiesTitles,
                             @RequestParam("date") String dateSending,
                             @RequestParam("files") Set<MultipartFile> files,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            return "redirect:/cards/add";
        }
    
        if(user.isAdmin())
            teacherService.create(firstname, lastname, patronymic, universitiesAbbreviation, facultiesTitles, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.TEACHER,
                    teacherService.create(firstname, lastname, patronymic, universitiesAbbreviation, facultiesTitles, files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        return createCard(model);
    }
    
    @GetMapping("/get")
    public String getCard(@AuthenticationPrincipal User user,
                                 @RequestParam("id") Long cardId,
                                 @RequestParam("cardType") String cardType,
                                 Model model) {
        Card card;
        switch (cardType) {
            case "university" -> model.addAttribute("university", card = universityService.findById(cardId));
            case "dormitory" -> model.addAttribute("dormitory", card = dormitoryService.findById(cardId));
            case "faculty" -> model.addAttribute("faculty", card = facultyService.findById(cardId));
            case "teacher" -> model.addAttribute("teacher", card = teacherService.findById(cardId));
            default -> throw new IllegalStateException("Unexpected value: " + cardType);
        }
            
        if(Objects.nonNull(card))
            model.addAttribute("numbers", listUtilities.getNumbers(card.getPhotos()));
        model.addAttribute("user", user);
        model.addAttribute("estimated", card.containsAssessor(user));
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        return "/".concat(cardType).concat("_card");
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit")
    public String editCard(@AuthenticationPrincipal User user,
                           @RequestParam("title") String cardTitle,
                           @RequestParam("type") String cardType,
                           @RequestParam("id") Long cardId,
                           @RequestParam(value = "files", required = false) Set<MultipartFile> files,
                           Model model) {
        
        switch (cardType) {
            case "university" -> universityService.edit(cardId, cardTitle, files);
            case "dormitory" -> dormitoryService.edit(cardId, cardTitle, files);
            case "faculty" -> facultyService.edit(cardId, cardTitle, files);
            case "teacher" -> teacherService.edit(cardId, cardTitle, files);
            default -> throw new IllegalStateException("Unexpected value: " + cardType);
            
        }
        
        return getCard(user, cardId, cardType, model);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete")
    public String deleteCard(@AuthenticationPrincipal User user,
                             @RequestParam("id") Long cardId,
                             @RequestParam("type") String cardType,
                             Model model) {
    
        switch (cardType) {
            case "university" -> universityService.deleteById(cardId);
            case "dormitory" -> dormitoryService.deleteById(cardId);
            case "faculty" -> facultyService.deleteById(cardId);
            case "teacher" -> teacherService.deleteById(cardId);
            default -> throw new IllegalStateException("Unexpected value: " + cardType);
        }
        
        cardType = (cardType.equals("teacher")) ? cardType.concat("s") : cardType.replace("y", "ies");
        
        return cardsList(user, cardType, model);
    }
}
