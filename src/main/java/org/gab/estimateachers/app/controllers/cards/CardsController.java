package org.gab.estimateachers.app.controllers.cards;

import org.gab.estimateachers.app.services.DormitoryService;
import org.gab.estimateachers.app.services.FacultyService;
import org.gab.estimateachers.app.services.TeacherService;
import org.gab.estimateachers.app.services.UniversityService;
import org.gab.estimateachers.app.utilities.CardsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.*;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Qualifier("usersUtilities")
    private UsersUtilities usersUtilities;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("cardsUtilities")
    private CardsUtilities cardsUtilities;

    @GetMapping("/")
    public String cardsMenu(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        
        return "/cards_menu";
    }
    
    @GetMapping("/{cardsType}")
    public String cardsList(@AuthenticationPrincipal User user,
                                   @PathVariable("cardsType") String cardsType,
                                   Model model) {
        
        model.addAttribute("listName", cardsType.substring(0, 1).toUpperCase().concat(cardsType.substring(1)));
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        Object list;
        String cardType = (cardsType.equals("teachers")) ? cardsType.substring(1) : cardsType.replace("ies", "y");
        
        switch(cardsType) {
            
            case "universities": {
               
                list = universityService.findAll();
                
                break;
            }
            case "dormitories": {
        
                list = dormitoryService.findAll();
        
                break;
            }
            case "faculties": {
        
                list = facultyService.findAll();
        
                break;
            }
            case "teachers": {
        
                list = teacherService.findAll();
        
                break;
            }
            default: {
                
                list = null;
            }
        }
        
        model.addAttribute("cardType", cardType);
        model.addAttribute("cards", list);
        
        return "/cards_list";
    }
    
    @PostMapping("/add/university")
    public String addUniversity(@RequestParam("title") String universityTitle,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        universityService.create(new University(universityTitle));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/dormitory")
    public String addDormitory(@RequestParam("title") String dormitoryTitle,
                               @RequestParam("universityId") Long universityId,
                               Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        dormitoryService.create(new Dormitory(dormitoryTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/faculty")
    public String addFaculty(@RequestParam("title") String facultyTitle,
                             @RequestParam("universityId") Long universityId,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        facultyService.save(new Faculty(facultyTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/teacher")
    public String addTeacher(@RequestParam("firstname") String firstname,
                             @RequestParam("lastname") String lastname,
                             @RequestParam("patronymic") String patronymic,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
        
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        teacherService.create(new Teacher(firstname, lastname, patronymic));
        
        return "/process_application_first";
    }
    
    @PostMapping("/edit/university")
    public String editUniversity(@RequestParam("title") String universityTitle,
                                 Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        universityService.create(new University(universityTitle));
        
        return "/process_application_first";
    }
    
    @PostMapping("/edit/dormitory")
    public String editDormitory(@RequestParam("title") String dormitoryTitle,
                                @RequestParam("universityId") Long universityId,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        
        dormitoryService.save(new Dormitory(dormitoryTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/edit/faculty")
    public String editFaculty(@RequestParam("title") String facultyTitle,
                              @RequestParam("universityId") Long universityId,
                              Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        facultyService.save(new Faculty(facultyTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/edit/teacher")
    public String editTeacher(@RequestParam("firstname") String firstname,
                              @RequestParam("lastname") String lastname,
                              @RequestParam("patronymic") String patronymic,
                              Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            return "";
        }
        
        teacherService.save(new Teacher(firstname, lastname, patronymic));
        
        return "/process_application_first";
    }
    
    @PostMapping("/cards/get")
    public String universityCard(@AuthenticationPrincipal User user,
                                 @RequestParam("id") Long id,
                                 @RequestParam("cardType") String cardType,
                                 Model model) {
        Object card;
        
        switch (cardType) {
        
            case "university": {
            
                card = universityService.findById(id);
                break;
            }
            case "dormitory": {
        
                card = dormitoryService.findById(id);
                break;
            }
            case "faculty": {
        
                card = facultyService.findById(id);
                break;
            }
            case "teacher": {
        
                card = teacherService.findById(id);
                break;
            }
            default: {
        
                card = null;
                break;
            }
        }
        
        model.addAttribute("card", card);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        
        return "/".concat(cardType).concat("_card");
    }
}
