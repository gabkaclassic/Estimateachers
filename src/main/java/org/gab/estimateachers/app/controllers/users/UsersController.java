package org.gab.estimateachers.app.controllers.users;

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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            @RequestParam(name = "profilePhoto") MultipartFile profilePhoto,
            @RequestParam(name = "cardPhoto") MultipartFile cardPhoto,
            Model model
    ) {
    
        List<String> remarks = new ArrayList<>();
        boolean isCorrectData = usersUtilities.checkUserDataFromRegistration(
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
    
    @GetMapping("/edit/{id}")
    public String edit(@AuthenticationPrincipal User currentUser,
                       @PathVariable(value = "id") Long userId,
                       Model model) {
        
        if(Objects.isNull(currentUser)
                || (!currentUser.isAdmin() && !currentUser.getId().equals(userId)))
            return "/error_forbidden";
        
        model.addAttribute("user", (currentUser.getId().equals(userId)) ?
                currentUser : userService.findById(userId));
        model.addAttribute("isAdmin", currentUser.isAdmin());
        
        return "/user_edit";
    }
    
    @PostMapping("/edit/{id}")
    public String egitProcess(@PathVariable("id") Long userId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              Model model) {
        
        List<String> remarks = new ArrayList<>();
        User user = userService.findById(userId);
        
        usersUtilities.checkLoginWithoutUnique(username, remarks);
        usersUtilities.checkPassword(password, remarks);
        if(Objects.nonNull(user.getEmail())
                && !user.getEmail().equals(email))
            usersUtilities.checkEmail(email, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
            return edit(user, userId, model);
        }
        
        userService.update(userId, username, password, email);
        
        return "/homepage";
    }
}
