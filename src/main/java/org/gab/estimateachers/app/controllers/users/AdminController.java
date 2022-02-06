package org.gab.estimateachers.app.controllers.users;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Value("${spring.mail.username}")
    private String supportEmail;
    
    protected final String ERROR_MESSAGE = """
            Error occurred: %s
            Reason: %s
            Error occurred. To prevent this from happening again, please help our service: send this message in the form of a screenshot/copied text,
            along with the current time and, if possible, the actions that you performed before this error occurred, to our employee at the email address: %s \n
            Thank you for helping our service develop. Please go to the start page of the service.
            """;
    
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
        
        return "redirect:" + request.getHeader("Referer");
    }
    
    @GetMapping("/allUsers")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String showAllUsers(Model model) {
        
        model.addAttribute("users", listUtilities.getUsersList());
        
        return "/users_list";
    }
    
    @PostMapping("/search/login")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    
    @PostMapping("/delete")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String deleteUser(@RequestParam("userId") Long userId) {
        
        userService.deleteById(userId);
        
        return "redirect:/admin/allUsers";
    }
    
    
    @PostMapping("/search/id")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String createNewAdmin() {
        
        return "/add_admin";
    }
    
    @PostMapping("/add")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    
    @Recover
    @PostMapping("/error")
    @GetMapping("/error")
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An error on the server side or a click on an invalid link")
    public ModelAndView error(Exception exception) {
        
        ModelAndView model = new ModelAndView("Error");
        model.addObject("Error",
                String.format(ERROR_MESSAGE, exception.getMessage(), exception.getCause(), supportEmail)
        );
        
        return model;
    }
}