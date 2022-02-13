package org.gab.estimateachers.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class MailConfiguration {
    
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${mail.debug}")
    private String debug;
    
    
    @Bean
    public JavaMailSender getMailSender() {
    
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        
        sender.setHost(host);
        sender.setUsername(username);
        sender.setProtocol(protocol);
        sender.setPassword(password);
        sender.setPort(port);
        
        Properties properties = sender.getJavaMailProperties();
        
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);
        
        log.info("Created bean mailSender");
        
        return sender;
    }

}
