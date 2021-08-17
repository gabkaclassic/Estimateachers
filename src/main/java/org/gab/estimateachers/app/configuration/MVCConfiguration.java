package org.gab.estimateachers.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
    
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/users").setViewName("users_logic");
        registry.addViewController("/users/login").setViewName("login");
        
    }
}
