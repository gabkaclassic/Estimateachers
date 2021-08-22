package org.gab.estimateachers;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Log4j
@SpringBootApplication
@EnableJpaRepositories("org.gab.estimateachers.app.repositories")
@EntityScan(basePackages = {"org.gab.estimateachers.entities"})
@PropertySources(
        @PropertySource("classpath:application.properties")
)
@ComponentScans(
        @ComponentScan("org.gab.estimateachers")
)
public class EstimateachersApplication {
    
    public static void main(String[] args) {
        
        EstimateachersApplication app = new EstimateachersApplication();

        log.info("App started");

        app.start(args);

        log.info("App stopped");
    }
    
    private void start(String[] args) {
    
        SpringApplication.run(EstimateachersApplication.class, args);
        
    }

}
