package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    private static final String HOME_DIRECTORY_TEMPLATE = "/users/";
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/registry")
    public String registryPage() {
        
        return HOME_DIRECTORY_TEMPLATE.concat("registry");
    }
    
    @PostMapping("/registry")
    public String signUp(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Map<String, Object> model
    ) {
        
        model.clear();
        
        List<String> remarks = new ArrayList<>();
        
        UsersUtilities.checkUserData(login, password, email, remarks);
        
        if(userRepository.existsByLogin(login))
            remarks.add("Entered login is already in use");
        if(Objects.nonNull(email) && userRepository.existsByEmail(email))
            remarks.add("Entered email is already in use");
            
    
        if(!remarks.isEmpty()) {
            
            model.put("remarks", remarks);
            
            
            return "/users/registry";
        }
        
        User user = new User(login, email, password);
        
        userRepository.save(user);
        
        return "redirect:/users/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        
        
        return HOME_DIRECTORY_TEMPLATE.concat("login");
    }
    
//    @PostMapping("/login")
//    public String signIn(
//            @RequestParam(name = "login") String login,
//            @RequestParam(name = "password") String password,
//            Map<String, Object> model
//    ) {
//
//        User user = userRepository.findByLogin(login);
//
//        if(Objects.nonNull(user) &&
//                user.getPassword().equals(password))
//            model.put("state", "Success sign in");
//        else
//            model.put("state", "Invalid login or password");
//
//        System.out.println(model.get("state").toString().toLowerCase());
//
//        return HOME_DIRECTORY_TEMPLATE.concat("login");
//    }
    
    @GetMapping("/allUsers")
    public String showAllUsers(Map<String, Object> model) {
    
        Iterable<User> users = userRepository.findAll();
    
        model.put("users", users);
        
        return HOME_DIRECTORY_TEMPLATE.concat("listOfAllUsers");
    }
}
