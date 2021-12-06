package org.gab.estimateachers.app.controllers.cards;

import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.CardsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.system.CardType;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Object list;
        String cardType = (cardsType.equals("teachers")) ? cardsType.substring(1) : cardsType.replace("ies", "y");
    
        switch (cardsType) {
            case "universities" -> list = universityService.findAll();
            case "dormitories" -> list = dormitoryService.findAll();
            case "faculties" -> list = facultyService.findAll();
            case "teachers" -> list = teacherService.findAll();
            default -> list = Collections.emptyList();
        }
        
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
                                @RequestParam("date") String dateSending,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks);
    
        if(!remarks.isEmpty())
            model.addAttribute("remarks", remarks);
           
        if(user.isAdmin())
            universityService.create(universityTitle);
        else
            creatingCardApplicationService.create(CardType.UNIVERSITY, universityService.create(universityTitle), user, dateSending);
        
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/dormitory")
    public String addDormitory(@AuthenticationPrincipal User user,
                               @RequestParam("title") String dormitoryTitle,
                               @RequestParam("university") String universityAbbreviation,
                               @RequestParam("date") String dateSending,
                               Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks);
    
        if(!remarks.isEmpty())
            model.addAttribute("remarks", remarks);
    
        if(user.isAdmin())
            dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation));
        else
            creatingCardApplicationService.create(CardType.DORMITORY, dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation)), user, dateSending);
        
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/faculty")
    public String addFaculty(@AuthenticationPrincipal User user,
                             @RequestParam("title") String facultyTitle,
                             @RequestParam("university") String universityAbbreviation,
                             @RequestParam("date") String dateSending,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks);
    
        if(!remarks.isEmpty())
            model.addAttribute("remarks", remarks);
        
        facultyService.save(new Faculty(facultyTitle, universityService.findByAbbreviation(universityAbbreviation)));
    
        if(user.isAdmin())
            facultyService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation));
        else
            creatingCardApplicationService.create(CardType.DORMITORY, dormitoryService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation)), user, dateSending);
            
        return "redirect:/cards/add";
    }
    
    @PostMapping("/add/teacher")
    public String addTeacher(@AuthenticationPrincipal User user,
                             @RequestParam("firstName") String firstname,
                             @RequestParam("lastName") String lastname,
                             @RequestParam("patronymic") String patronymic,
                             @RequestParam("email") String email,
                             @RequestParam("universities") Set<String> universitiesAbbreviation,
                             @RequestParam("faculties") Set<String> facultiesTitles,
                             @RequestParam("date") String dateSending,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
        usersUtilities.checkEmail(email, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            return "redirect:/cards/add";
        }
    
        if(user.isAdmin())
            teacherService.create(firstname, lastname, patronymic, email, universitiesAbbreviation, facultiesTitles);
        else
            creatingCardApplicationService.create(CardType.TEACHER, teacherService.create(firstname, lastname, patronymic, email, universitiesAbbreviation, facultiesTitles), user, dateSending);
        
        return "redirect:/cards/add";
    }
    
    @PostMapping("/cards/get")
    public String universityCard(@AuthenticationPrincipal User user,
                                 @RequestParam("id") Long id,
                                 @RequestParam("cardType") String cardType,
                                 Model model) {
        Object card;
    
        switch (cardType) {
            case "university" -> card = universityService.findById(id);
            case "dormitory" -> card = dormitoryService.findById(id);
            case "faculty" -> card = facultyService.findById(id);
            case "teacher" -> card = teacherService.findById(id);
            default -> card = null;
        }
        
        model.addAttribute("card", card);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        
        return "/".concat(cardType).concat("_card");
    }
}
