package org.gab.estimateachers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.remoting.jaxws.AbstractJaxWsServiceExporter;

@SpringBootApplication
@EnableJpaRepositories("org.gab.estimateachers.app.repositories")
@EntityScan(basePackages = {"org.gab.estimateachers.entities"})
@PropertySources({
        @PropertySource("classpath:application.properties")
})
@ComponentScans(
        @ComponentScan("org.gab.estimateachers")
)
public class EstimateachersApplication {
    
    public static void main(String[] args) {
        
        EstimateachersApplication app = new EstimateachersApplication();

        app.start(args);
        
    }
    
    private void start(String[] args) {

        SpringApplication.run(EstimateachersApplication.class, args);
    }
    
}
