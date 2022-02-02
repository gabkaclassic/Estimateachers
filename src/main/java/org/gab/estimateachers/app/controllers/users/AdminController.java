package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    
    @GetMapping(value = {
            "/search/id",
            "/search/login"
    })
    public String plug(HttpServletRequest request) {
        
        return "redirect:" + request.getHeader("Referer");
    }
    
    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        return "/users_list";
    }
    
    @PostMapping("/search/login")
    public String findUserByLogin(@RequestParam(value = "username", required = false) String login,
                                  Model model) {
        
        if(Objects.nonNull(login) && !login.isEmpty()) {
            model.addAttribute("users", listUtilities.getFilteredUsersList(login));
            model.addAttribute("username", login);
        }
        else
            return "redirect:/admin/allUsers";
        
        return "/users_list";
    }
    
    @PostMapping("/search/id")
    public String findById(@RequestParam(value = "id", required = false) Long id,
                                  Model model) {
    
        if(Objects.nonNull(id)) {
            User user = userService.findById(id);
            model.addAttribute("users", (Objects.isNull(user)) ? Collections.emptyList() : List.of(user));
            model.addAttribute("id", id);
        }
        else
            return "redirect:/admin/allUsers";
        
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