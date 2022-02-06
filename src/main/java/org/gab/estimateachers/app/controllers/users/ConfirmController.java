package org.gab.estimateachers.app.controllers.users;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/confirm")
public class ConfirmController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Value("${spring.mail.username}")
    private String supportEmail;
    
    protected final String ERROR_MESSAGE = """
            Error occurred: %s
            Reason: %s
            Error occurred. To prevent this from happening again, please help our service: send this message in the form of a screenshot/copied text,
            along with the current time and, if possible, the actions that you performed before this error occurred, to our employee at the email address: %s \n
            Thank you for helping our service develop. Please go to the start page of the service.
            """;
    
    private static final String SUCCESS_CONFIRM_EMAIL_TEXT = """
            You have successfully confirmed your email address,
            now wait for our administrators to review your application.
            """;
    
    private static final String FAIL_CONFIRM_EMAIL_TEXT = """
                    Unfortunately, you could not successfully confirm your email address.
                    If you are sure that you have correctly filled out all your data in the registration form and followed the instructions from the letter,
                    write to our administrators about the error:
                    """;
    
    @Value("${spring.mail.adminEmail}")
    private String adminEmail;
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @GetMapping("/{activationCode}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String confirmEmail(@PathVariable("activationCode") String activationCode,
                               Model model) {
        
        if(userService.confirmEmail(activationCode)) {
            model.addAttribute("status", "success");
            model.addAttribute("text", SUCCESS_CONFIRM_EMAIL_TEXT);
    
            log.info("The user has successful verified the mail");
        }
        else {
            model.addAttribute("status", "danger");
            model.addAttribute("text", FAIL_CONFIRM_EMAIL_TEXT + adminEmail);
            
            log.info("The user has failed verified the mail");
        }
        
        return "/confirm_result";
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
