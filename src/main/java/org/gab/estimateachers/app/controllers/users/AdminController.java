package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.services.CreatingCardApplicationService;
import org.gab.estimateachers.app.services.RegistrationApplicationService;
import org.gab.estimateachers.app.services.UniversityService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    
    @Autowired
    @Qualifier("usersUtilities")
    private UsersUtilities usersUtilities;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
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
    
    @GetMapping("/add")
    public String createNewAdmin() {
        
        return "/add_admin";
    }
    
    @PostMapping("/add")
    public String saveAdmin(@RequestParam("username") String login,
                            @RequestParam("password") String password,
                            Model model) {
        
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkLogin(login, remarks);
        usersUtilities.checkPassword(password, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
            
            return "/add_admin";
        }
        
        userService.createAdmin(login, password);
        
        return "/add_admin";
    }
}