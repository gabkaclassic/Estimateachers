package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.services.CreatingCardApplicationService;
import org.gab.estimateachers.app.services.RegistrationApplicationService;
import org.gab.estimateachers.app.services.UniversityService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("registrationApplicationService")
    private RegistrationApplicationService registrationApplicationService;
    
    @Autowired
    @Qualifier("creatingCardApplicationService")
    private CreatingCardApplicationService creatingCardApplicationService;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        return "/users_list";
    }
    
    @PostMapping("/allUsers")
    public String findUserByLogin(@RequestParam("filter") String login,
                                  Model model) {
        
        model.addAttribute("users", listUtilities.getFilteredUsersList(login));
        
        return "/users_list";
    }
    
    @GetMapping("/applications/users")
    public String newUserApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getRegistrationApplicationList());
        
        return "/applications";
    }
    
    @GetMapping("/applications/cards")
    public String newCardApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getCreatingCardApplicationList());
        
        return "/applications";
    }
    
    @GetMapping("/applications/processing/first/{id}")
    public String processingApplicationFirstStep(@PathVariable(name = "id") Long applicationId,
                                        Model model) {
        
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
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
    
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
        
        model.addAttribute("application", application);
        
        return "/process_application_second";
    }
    
    @PostMapping("/applications/processing/second/{id}")
    public String processingApplicationSecondStepSave(@PathVariable(name = "id") Long applicationId,
                                                     @RequestParam("faculty") String facultyTitle,
                                                     @RequestParam("dormitory") String dormitoryTitle,
                                                     @RequestParam("course") Integer course,
                                                     Model model) {
        
        registrationApplicationService.apply(applicationId, facultyTitle, dormitoryTitle, course);
        
        return "redirect:/admin/applications";
    }
    
    @PostMapping("/applications/reject/{id}")
    public String rejectApplication(@PathVariable(name = "id") Long applicationId) {
        
        registrationApplicationService.deleteById(applicationId);
        
        return "redirect:/admin/applications";
    }
    
}
