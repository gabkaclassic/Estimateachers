package org.gab.estimateachers.app.controllers.homepage;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.services.UserService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomePageController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @GetMapping("/")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String homepage(@AuthenticationPrincipal User user) {
        
        return "/homepage";
    }
}
