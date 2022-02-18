package org.gab.estimateachers.app.controllers.homepage;

import org.gab.estimateachers.app.controllers.Errors.ControllerException;
import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Controller
@RequestMapping("/")
public class HomePageController {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @GetMapping("/")
    @Retryable(maxAttempts = 5, value = ControllerException.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String homepage(@AuthenticationPrincipal User user) throws ControllerException {
        
        return "/homepage";
    }
}
