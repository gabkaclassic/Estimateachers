package org.gab.estimateachers.app.controllers.users;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.services.StudentService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/users")
public class UsersController {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @Autowired
    @Qualifier("studentService")
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

        log.trace("Get query registration page");
        
        model.addAttribute("genders", listUtilities.getGendersList());
     
        return "/registry";
    }
    
    @PostMapping("/registry")
    public String signUp(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "patronymic", required = false) String patronymic,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "genders") String genderName,
            @RequestParam(name = "profilePhoto") MultipartFile profilePhoto,
            @RequestParam(name = "cardPhoto") MultipartFile cardPhoto,
            @RequestParam(name = "date") String date,
            Model model
    ) {
    
        log.trace("User registration process");
        
        List<String> remarks = new ArrayList<>();
        boolean isCorrectData = usersUtilities.checkUserDataFromRegistration(
                firstName,
                lastName,
                patronymic,
                username,
                password,
                email,
                cardPhoto,
                remarks
        );
        model.addAttribute("remarks", remarks);
        
        if(!isCorrectData) {
            
            log.info("Incorrect user data: ".concat(String.join("; ", remarks)));
            model.addAttribute("username", username);
            model.addAttribute("firstname", firstName);
            model.addAttribute("lastname", lastName);
            model.addAttribute("patronymic", patronymic);
            model.addAttribute("password", password);
            model.addAttribute("email", email);
            
            return registryPage(model);
        }
        
        studentService.sendApplication(
                firstName,
                lastName,
                patronymic,
                Genders.valueOf(genderName.toUpperCase()),
                profilePhoto,
                cardPhoto,
                date,
                userService.createUser(username, password, email)
        );
        
        log.trace("Successful process registration");
        
        return "redirect:/users/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        
        log.trace("Get query login page");
        
        return "/login";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@AuthenticationPrincipal User currentUser,
                       @PathVariable(value = "id") Long userId,
                       Model model) {
        
        log.trace("Get query edit profile page");
        
        if(Objects.isNull(currentUser)
                || (!currentUser.isAdmin() && !currentUser.getId().equals(userId))) {
    
            log.info("Error forbidden or user is null");
            
            return "/error_forbidden";
        }
        User user = userService.findById(userId);
        model.addAttribute("user", (currentUser.getId().equals(userId)) ?
                currentUser : user);
        model.addAttribute("isAdmin", currentUser.isAdmin());
        if(currentUser.isAdmin()) {
            model.addAttribute("adminRole", user.isAdmin());
            model.addAttribute("userRole", user.isUser());
            model.addAttribute("lockedRole", user.isLocked());
        }
        
        return "/user_edit";
    }
    
    @PostMapping("/edit/{id}")
    public String egitProcess(@AuthenticationPrincipal User currentUser,
                              @PathVariable("id") Long userId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              @RequestParam("profilePhoto") MultipartFile profilePhoto,
                              @RequestParam(value = "userRole", required = false) Boolean userRole,
                              @RequestParam(value = "adminRole", required = false) Boolean adminRole,
                              @RequestParam(value = "lockedRole", required = false) Boolean lockedRole,
                              Model model) {
        
        log.trace("User edit profile process");
        
        List<String> remarks = new ArrayList<>();
        User user = userService.findById(userId);
        usersUtilities.checkLoginWithoutUnique(username, remarks);
        usersUtilities.checkPassword(password, remarks);
        if(currentUser.isAdmin())
            usersUtilities.checkRoles(userRole, adminRole, lockedRole, user, remarks);
        if(!(Objects.isNull(profilePhoto) || profilePhoto.isEmpty()))
            usersUtilities.checkFile(profilePhoto, remarks);
        
        if(Objects.nonNull(user.getEmail())
                && !user.getEmail().equals(email))
            usersUtilities.checkEmail(email, remarks);
        
        if(!remarks.isEmpty()) {
            
            log.info("Incorrect data: ".concat(String.join("; ", remarks)));
            
            model.addAttribute("remarks", remarks);
            
            return edit(user, userId, model);
        }
        
        if(!(Objects.isNull(profilePhoto) || profilePhoto.isEmpty()))
            userService.update(userId, username, password, email, profilePhoto);
        else
            userService.update(userId, username, password, email);
        
        log.trace("Successful process edit profile");
        
        return "/homepage";
    }
}
