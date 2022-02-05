package org.gab.estimateachers.app.controllers.homepage;

import org.gab.estimateachers.app.services.UserService;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomePageController {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @GetMapping("/")
    public String homepage(@AuthenticationPrincipal User user) {
        
        if(Objects.nonNull(user)) {
            
            user.setOnline(true);
            userService.save(user);
        }
        
        return "/homepage";
    }
    
}
