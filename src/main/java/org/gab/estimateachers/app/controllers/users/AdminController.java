package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.services.ApplicationService;
import org.gab.estimateachers.app.services.DormitoryService;
import org.gab.estimateachers.app.services.FacultyService;
import org.gab.estimateachers.app.services.UniversityService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("applicationService")
    private ApplicationService applicationService;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @GetMapping("/")
    public String adminPanel(@AuthenticationPrincipal User admin,
                             Model model) {
        
        model.addAttribute("admin", admin);
        
        return "/adminPanel";
    }
    
    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        return "/users_list";
    }
    
    @GetMapping("/applications")
    public String applicationsPage(Model model) {
        
        model.addAttribute("applications", listUtilities.getApplicationList());
        
        return "/applications";
    }
    
    @GetMapping("/applications/processing/first/{id}")
    public String processingApplicationFirstStep(@PathVariable(name = "id") Long applicationId,
                                        Model model) {
        
        Application application = applicationService.findById(applicationId);
        model.addAttribute("universities", listUtilities.getUniversitiesAbbreviationsList());
        model.addAttribute("application", application);
        
        return "/process_application_first";
    }
    
    @PostMapping("/applications/processing/first/{id}")
    public String processingApplicationFirstStepSave(@PathVariable(name = "id") Long applicationId,
                                                 @RequestParam("course") Integer course,
                                                 @RequestParam("university") String abbreviationUniversity,
                                                 Model model) {
        
        University university = universityService.findByAbbreviation(abbreviationUniversity);
        
        model.addAttribute("dormitories", listUtilities.getDormitoriesTitlesList(abbreviationUniversity));
        model.addAttribute("faculties", listUtilities.getFacultiesTitlesList(abbreviationUniversity));
        model.addAttribute("university", university);
        model.addAttribute("course", course);
        
        return processingApplicationSecondStep(applicationId, model);
    }
    
    @GetMapping("/applications/processing/second/{id}")
    public String processingApplicationSecondStep(@PathVariable(name = "id") Long applicationId,
                                                 Model model) {
        
        Application application = applicationService.findById(applicationId);
        
        model.addAttribute("application", application);
        
        return "/process_application_second";
    }
    
    @PostMapping("/applications/processing/second/{id}")
    public String processingApplicationSecondStepSave(@PathVariable(name = "id") Long applicationId,
                                                     @RequestParam("faculty") String facultyTitle,
                                                     @RequestParam("dormitory") String dormitoryTitle,
                                                     @RequestParam("course") Integer course,
                                                     Model model) {
        
        applicationService.apply(applicationId, facultyTitle, dormitoryTitle, course);
        
        return "redirect:/admin/applications";
    }
    
    @PostMapping("/applications/reject/{id}")
    public String rejectApplication(@PathVariable(name = "id") Long applicationId) {
        
        applicationService.deleteById(applicationId);
        
        return "redirect:/applications";
    }
}
