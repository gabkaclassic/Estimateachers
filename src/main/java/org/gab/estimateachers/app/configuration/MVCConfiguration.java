package org.gab.estimateachers.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    
        registry.addViewController("/users/login").setViewName("login");
        
        log.info("Added view controllers");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + uploadPath + System.getProperty("file.separator"));
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        log.info("Added resource handlers");
    }

    @Bean("restTemplate")
    public RestTemplate getRestTemplate() {
        
        log.info("Created bean rest template");
        
        return new RestTemplate();
    }
    
    public void addInterceptors(InterceptorRegistry registry) {
        
        registry.addInterceptor(new RedirectInterceptor());
        
        log.info("Added interceptors from Turbolinks");
    }
}
