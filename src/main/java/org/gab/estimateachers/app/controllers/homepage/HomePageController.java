package org.gab.estimateachers.app.controllers.homepage;

import org.gab.estimateachers.entities.system.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomePageController {
    
    @GetMapping("/")
    public String homepage(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", (!Objects.isNull(user) && user.isAdmin()));
        
        return "/homepage";
    }
    
}
