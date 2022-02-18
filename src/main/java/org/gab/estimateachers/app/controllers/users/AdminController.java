package org.gab.estimateachers.app.controllers.users;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.controllers.Errors.ControllerException;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
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
            "/search/login",
            "/delete"
    })
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
    
        String header = request.getHeader("Referer");
    
        log.info("A plug has triggered in admin controller, the request has been redirected to: " + header);
    
        return "redirect:" + header;
    }
    
    @GetMapping("/allUsers")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String showAllUsers(@AuthenticationPrincipal User admin,
                               Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        log.info(String.format("Admin with ID (%s) requested a list of all users", admin.getId().toString()));
        
        return "/users_list";
    }
    
    @PostMapping("/search/login")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String findUserByLogin(@AuthenticationPrincipal User admin,
                                  @RequestParam(value = "username", required = false) String login,
                                  Model model) {
        
        if(Objects.nonNull(login) && !login.isEmpty()) {
            model.addAttribute("users", listUtilities.getFilteredUsersList(login));
            model.addAttribute("username", login);
        }
        else
            return "redirect:/admin/allUsers";
    
        log.info(String.format("Admin with ID (%s) requested and searched for the user by nickname", admin.getId().toString()));
        
        return "/users_list";
    }
    
    @PostMapping("/delete")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String deleteUser(@AuthenticationPrincipal User admin,
                             @RequestParam("userId") Long userId) {
        
        userService.deleteById(userId);
    
        log.info(String.format("Admin with ID (%s) deleted the user account", admin.getId().toString()));
        
        return "redirect:/admin/allUsers";
    }
    
    
    @PostMapping("/search/id")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String findById(@AuthenticationPrincipal User admin,
                           @RequestParam(value = "id", required = false) Long id,
                           Model model) {
    
        if(Objects.nonNull(id)) {
            User user = userService.findById(id);
            model.addAttribute("users", (Objects.isNull(user)) ? Collections.emptyList() : List.of(user));
            model.addAttribute("id", id);
        }
        else
            return "redirect:/admin/allUsers";
    
        log.info(String.format("Admin with ID (%s) requested and searched for the user by ID", admin.getId().toString()));
        
        return "/users_list";
    }
    
    @GetMapping("/add")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String createNewAdmin(@AuthenticationPrincipal User admin) {
        
        log.info(String.format("Admin with ID (%s) opened the panel to create a new administrator", admin.getId().toString()));
        
        return "/add_admin";
    }
    
    @PostMapping("/add")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String saveAdmin(@AuthenticationPrincipal User admin,
                            @RequestParam("username") String login,
                            @RequestParam("password") String password,
                            Model model) {
        
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkLogin(login, remarks);
        usersUtilities.checkPassword(password, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            log.info(String.format("Admin with ID (%s) failed created a new administrator", admin.getId().toString()));
            
            return "/add_admin";
        }
        
        userService.createAdmin(login, password);
    
        log.info(String.format("Admin with ID (%s) successful created a new administrator", admin.getId().toString()));
        
        return "/add_admin";
    }
}