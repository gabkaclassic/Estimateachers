package org.gab.estimateachers.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    
        registry.addViewController("/").setViewName("homeController");
        registry.addViewController("/users").setViewName("usersController");
        registry.addViewController("/admin").setViewName("adminsController");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
}
