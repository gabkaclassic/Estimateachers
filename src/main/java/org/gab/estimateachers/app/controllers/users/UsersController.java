package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.app.services.StudentService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("usersUtilities")
    private UsersUtilities usersUtilities;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @GetMapping("/registry")
    public String registryPage(Model model) {

        model.addAttribute("genders", listUtilities.getGendersList());
     
        return "/registry";
    }
    
    @PostMapping("/registry")
    public String signUp(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "genders") String genderName,
            @RequestParam(name = "age") Integer age,
            @RequestParam(name = "profilePhoto") MultipartFile profilePhoto,
            @RequestParam(name = "cardPhoto") MultipartFile cardPhoto,
            Model model
    ) {
    
        List<String> remarks = new ArrayList<>();
        boolean isCorrectData = usersUtilities.checkUserData(
                firstName,
                lastName,
                username,
                password,
                email,
                cardPhoto,
                remarks
        );
        model.addAttribute("remarks", remarks);
        
        if(!isCorrectData)
            return registryPage(model);
        
        studentService.sendApplication(
                firstName,
                lastName,
                age,
                Genders.valueOf(genderName.toUpperCase()),
                profilePhoto,
                cardPhoto,
                new User(username, email, password)
        );
        
        return "redirect:/users/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        
        return "/login";
    }
    
    @PostMapping("/allUsers")
    @GetMapping("/allUsers")
    public String showAllUsers(Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        return "/listOfAllUsers";
    }
}
