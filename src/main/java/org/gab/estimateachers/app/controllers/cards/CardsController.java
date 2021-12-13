package org.gab.estimateachers.app.controllers.cards;

import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.CardsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.CardType;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        String cardType = (cardsType.equals("teachers")) ? cardsType.substring(1) : cardsType.replace("ies", "y");
        List<? extends Card> list;
        
        switch (cardsType) {
            case "universities" -> list = universityService.findAll();
            case "dormitories" -> list = dormitoryService.findAll();
            case "faculties" -> list = facultyService.findAll();
            case "teachers" -> list = teacherService.findAll();
            default -> list = Collections.emptyList();
        }
        
        model.addAttribute("numbers", Stream.iterate(1, n -> n+1).limit(list.size()).collect(Collectors.toList()));
        model.addAttribute("cardType", cardType);
        model.addAttribute("cards", list);
        
        return "/cards_list";
    }
    
    @GetMapping("/add")
    public String createCard(Model model) {
        
        model.addAttribute("universities", listUtilities.getUniversitiesAbbreviationsList());
        model.addAttribute("faculties", listUtilities.getAllFacultiesTitlesList());
        
        return "/add_card_menu";
    }
    
    @PostMapping("/add/university")
    public String addUniversity(@AuthenticationPrincipal User user,
                                @RequestParam("title") String universityTitle,
                                @RequestParam(value = "bachelor", required = false) Boolean bachelor,
                                @RequestParam(value = "magistracy", required = false) Boolean magistracy,
                                @RequestParam(value = "specialty", required = false) Boolean specialty,
                                @RequestParam("date") String dateSending,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks, universityService);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
        
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            universityService.create(universityTitle, bachelor, magistracy, specialty);
        else
            creatingCardApplicationService.create(CardType.UNIVERSITY, universityService.create(universityTitle, bachelor, magistracy, specialty), user, dateSending);
        
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/dormitory")
    public String addDormitory(@AuthenticationPrincipal User user,
                               @RequestParam("title") String dormitoryTitle,
                               @RequestParam("university") String universityAbbreviation,
                               @RequestParam("date") String dateSending,
                               Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks, dormitoryService);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
            
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation));
        else
            creatingCardApplicationService.create(
                    CardType.DORMITORY,
                    dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation)),
                    user,
                    dateSending
            );
        
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/faculty")
    public String addFaculty(@AuthenticationPrincipal User user,
                             @RequestParam("title") String facultyTitle,
                             @RequestParam("university") String universityAbbreviation,
                             @RequestParam("date") String dateSending,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks, facultyService);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
        
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            facultyService.create(facultyTitle, universityAbbreviation);
        else
            creatingCardApplicationService.create(
                    CardType.DORMITORY,
                    dormitoryService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation)),
                    user,
                    dateSending
            );
            
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/teacher")
    public String addTeacher(@AuthenticationPrincipal User user,
                             @RequestParam("firstName") String firstname,
                             @RequestParam("lastName") String lastname,
                             @RequestParam("patronymic") String patronymic,
                             @RequestParam("universities") Set<String> universitiesAbbreviation,
                             @RequestParam("faculties") Set<String> facultiesTitles,
                             @RequestParam("date") String dateSending,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            return "redirect:/cards/add";
        }
    
        if(user.isAdmin())
            teacherService.create(firstname, lastname, patronymic, universitiesAbbreviation, facultiesTitles);
        else
            creatingCardApplicationService.create(
                    CardType.TEACHER,
                    teacherService.create(firstname, lastname, patronymic, universitiesAbbreviation, facultiesTitles), user, dateSending
            );
        
        return "redirect:/cards/add";
    }
    
    @GetMapping("/get")
    public String universityCard(@AuthenticationPrincipal User user,
                                 @RequestParam("id") Long cardId,
                                 @RequestParam("cardType") String cardType,
                                 Model model) {
        Card card;
        
        switch (cardType) {
            case "university" -> model.addAttribute("university", card = universityService.findById(cardId));
            case "dormitory" -> model.addAttribute("dormitory", card = dormitoryService.findById(cardId));
            case "faculty" -> model.addAttribute("faculty", card = facultyService.findById(cardId));
            case "teacher" -> model.addAttribute("teacher", card = teacherService.findById(cardId));
            default -> card = null;
            }
            
        if(Objects.nonNull(card))
            model.addAttribute("numbers", listUtilities.getNumbers(card.getPhotos()));
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        
        return "/".concat(cardType).concat("_card");
    }
}
