package org.gab.estimateachers.app.controllers.users;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.configuration.CaptchaResponseDTO;
import org.gab.estimateachers.app.services.StudentService;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.system.users.Genders;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/users")
public class UsersController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Value("${spring.mail.username}")
    private String supportEmail;
    
    protected final String ERROR_MESSAGE = """
            Error occurred: %s
            Reason: %s
            Error occurred. To prevent this from happening again, please help our service: send this message in the form of a screenshot/copied text,
            along with the current time and, if possible, the actions that you performed before this error occurred, to our employee at the email address: %s
            
            Thank you for helping our service develop. Please go to the start page of the service.
            """;
    
    @Value("${captcha.secret}")
    private String secret;
    
    @Value("${captcha.url}")
    private String url;
    
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    
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
    
    @GetMapping(value = {
            "/logout",
            "/signout",
            "/signout/cancel"
    })
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
    
        String header = request.getHeader("Referer");
    
        log.info("A plug has triggered in users controller, the request has been redirected to: " + header);
    
        return "redirect:" + header;
    }
    
    @GetMapping("/registry")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String registryPage(Model model) {
        
        model.addAttribute("genders", listUtilities.getGendersList());
    
        log.info("Get query registration page");
        
        return "/registry";
    }
    
    @PostMapping("/registry")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String signUp(@RequestParam(name = "username") String username,
                         @RequestParam(name = "firstName") String firstName,
                         @RequestParam(name = "lastName") String lastName,
                         @RequestParam(name = "patronymic", required = false) String patronymic,
                         @RequestParam(name = "email") String email,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "genders") String genderName,
                         @RequestParam(name = "profilePhoto") MultipartFile profilePhoto,
                         @RequestParam(name = "cardPhoto") MultipartFile cardPhoto,
                         @RequestParam(name = "date") String date,
                         @RequestParam(name = "g-recaptcha-response") String response,
                         Model model) {
        
        response = response.substring(0, response.length()-1);
        
        List<String> remarks = new ArrayList<>();
        String templateUrl = String.format(url, secret, response);
        CaptchaResponseDTO responseDTO = restTemplate.postForObject(templateUrl, Collections.emptyList(), CaptchaResponseDTO.class);
        
        if(Objects.nonNull(responseDTO) && !responseDTO.isSuccess()) {
            
            remarks.addAll(responseDTO.getErrorCodes());
            log.info("Failed passage of Google reCaptcha");
        }
        
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
    
            log.info("Failed user registration process");
            
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
        
        log.info("Successful process registration");
        
        return "redirect:/users/login";
    }
    
    @GetMapping("/login")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String loginPage(Model model) {
        
        model.addAttribute("isAdmin", false);
        
        log.trace("Get query login page");
        
        return "/login";
    }
    
    @PostMapping("/login")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String signIn(@AuthenticationPrincipal User user,
                         HttpServletRequest request) {
    
        user.setOnline(true);
        userService.save(user);
        
        log.info("Successful authorization process");
        
        return "redirect:"+ request.getHeader("Referer");
    }
    
    @PostMapping("/signout")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String logout(@AuthenticationPrincipal User user,
                         HttpServletRequest request,
                         Model model) {
        
        user.setOnline(false);
        userService.save(user);
        model.addAttribute("link", request.getHeader("Referer"));
    
        log.info("Successful logout process (first step)");
        
        return "/logout_process";
    }
    
    @PostMapping("/signout/cancel")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String logoutCancel(@AuthenticationPrincipal User user,
                               @RequestParam("link") String link) {
        
        user.setOnline(true);
        userService.save(user);
    
        log.info("Successful logout process (second step)");
        
        return "redirect:" + link;
    }
    
    @GetMapping("/edit/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String edit(@AuthenticationPrincipal User currentUser,
                       @PathVariable(value = "id") Long userId,
                       Model model) throws Exception {
        
        if(Objects.isNull(currentUser)
                || (!currentUser.isAdmin() && !currentUser.getId().equals(userId))) {
    
            log.info(String.format("Failed profile change request (profile ID: %s, user ID: %s)",
                    userId.toString(), currentUser.getId().toString()));
            
            throw new Exception("Error forbidden or user is null");
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
        
        model.addAttribute("delete", (currentUser.isAdmin() && !user.equals(currentUser)));
    
        log.info(String.format("Successful profile change request (profile ID: %s, user ID: %s)",
                userId.toString(), currentUser.getId().toString()));
        
        return "/user_edit";
    }
    
    @PostMapping("/edit/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String egitProcess(@AuthenticationPrincipal User currentUser,
                              @PathVariable("id") Long userId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              @RequestParam("profilePhoto") MultipartFile profilePhoto,
                              @RequestParam(value = "userRole", required = false) Boolean userRole,
                              @RequestParam(value = "adminRole", required = false) Boolean adminRole,
                              @RequestParam(value = "lockedRole", required = false) Boolean lockedRole,
                              Model model) throws Exception {
        
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
            
            model.addAttribute("remarks", remarks);
    
            log.info(String.format("Failed profile change process (profile ID: %s, user ID: %s)",
                    userId.toString(), currentUser.getId().toString()));
            
            return edit(user, userId, model);
        }
        
        if(!(Objects.isNull(profilePhoto) || profilePhoto.isEmpty()))
            userService.update(userId, username, password, email, profilePhoto);
        else
            userService.update(userId, username, password, email);
    
        log.info(String.format("Successful profile change process (profile ID: %s, user ID: %s)",
                userId.toString(), currentUser.getId().toString()));
        
        return "/homepage";
    }
    
    @Recover
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An error on the server side or a click on an invalid link")
    public ModelAndView error(Exception exception) {
        
        ModelAndView model = new ModelAndView("Error");
        
        model.addObject("Error",
                String.format(ERROR_MESSAGE, exception.getMessage(), exception.getCause(), supportEmail)
        );
    
        log.warn(String.format("Exception: %s, reason: %s", exception.getMessage(), exception.getCause()));
        
        return model;
    }
}
