package org.gab.estimateachers.app.controllers.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class HomePageController {
    
    @GetMapping("/")
    public String homepage() {
        
        return "/homepage";
    }
    
    @PostMapping("/")
    public String f() {
        
        return "/homepage";
    }
    
}
