package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/registry")
    public String registryPage() {
        
        return "/registry";
    }
    
    @PostMapping("/registry")
    public String signUp(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model
    ) {
    
    
        List<String> remarks = new ArrayList<>();
        
        UsersUtilities.checkUserData(username, password, email, remarks);
        
        if(Objects.nonNull(username)
                && (!username.isEmpty())
                && userRepository.existsByUsername(username))
            remarks.add("Entered login is already in use");
        
        if(Objects.nonNull(email)
                && (!email.isEmpty())
                && userRepository.existsByEmail(email))
            remarks.add("Entered email is already in use");
    
        model.addAttribute("remarks", remarks);
        
        if(!remarks.isEmpty()) {
            
            return "/registry";
        }
        
        User user = new User(username, email, password);
        
        userRepository.save(user);
        
        return "redirect:/users/registry";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        
        
        return "/login";
    }
    
    @PostMapping("/allUsers")
    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
    
        Iterable<User> users = userRepository.findAll();
    
        model.addAttribute("users", users);
        
        return "/listOfAllUsers";
    }
}
