package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
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
    
    @GetMapping("/applications/processing/{id}")
    public String processingApplication(Model model) {
        
        
        
        return "/process_application";
    }
    
    @PostMapping("/applications/processing/{id}")
    public String processedApplication(Model model) {
        
        
        
        return "redirect:/admin/applications";
    }
}
