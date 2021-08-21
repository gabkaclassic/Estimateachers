package org.gab.estimateachers.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    
        registry.addViewController("/").setViewName("homepage.ftl");
        registry.addViewController("/users/registry").setViewName("registry.ftl");
        registry.addViewController("/users/login").setViewName("login.ftl");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
