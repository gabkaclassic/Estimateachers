package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/confirm")
public class ConfirmController {
    
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
    public String confirmEmail(@PathVariable("activationCode") String activationCode,
                               Model model) {
        
        if(userService.confirmEmail(activationCode)) {
            model.addAttribute("status", "success");
            model.addAttribute("text", SUCCESS_CONFIRM_EMAIL_TEXT);
        }
        else {
            model.addAttribute("status", "danger");
            model.addAttribute("text", FAIL_CONFIRM_EMAIL_TEXT + adminEmail);
        }
        
        return "/confirm_result";
    }
}
