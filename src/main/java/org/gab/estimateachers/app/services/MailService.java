package org.gab.estimateachers.app.services;

import org.gab.estimateachers.entities.system.User;
import org.junit.gen5.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service("mailService")
public class MailService {
    
    private static final String URL = "";
    
    @Value("${spring.mail.username}")
    private String from;
    private static final String SUCCESS_REGISTRATION = """
    Dear %s, (%s) your application has been successfully reviewed by the administrators
    (on %s).
    Welcome to our service Estimateachers:
    %s
    """;
    private static final String REJECT_REGISTRATION = """
    Dear %s, (%s) your registration application has been rejected by the administrators
    (on %s) for the following reasons:
    %s
    You can try to register again:
    %s
    Sincerely, your Estimateachers
    """;
    private static final String REJECT_CARD = """
    Dear %s, (%s) your application for adding cards has been rejected by the administrators
    (on %s) for the following reasons:
    %s
    Sincerely, your Estimateachers
    """;
    private static final String REJECT_REQUEST = """
    Dear %s, (%s) your request has been rejected by the administrators
    (on %s) for the following reasons:
    %s
    Sincerely, your Estimateachers
    """;
    private static final String APPLY_REQUEST = """
    Dear %s, (%s) your request has been applied by the administrators
    (on %s)
    Thank you for helping our service work
    """;
    private static final String APPLY_CARD = """
    Dear %s, (%s) your application for adding cards has been applied by the administrators
    (on %s)
    Thank you for helping our service work
    """;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
    @Autowired
    private JavaMailSender sender;
    
    private void send(String to, String from, String text) {
    
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        
        sender.send(message);
    }
    
    public void notifyAccessRegistration(User user) {
        
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                user.getEmail(),
                from,
                String.format(
                        SUCCESS_REGISTRATION,
                        user.getOwner().getTitle(),
                        user.getUsername(),
                        LocalDateTime.now().format(DATE_FORMATTER),
                        URL
                )
        );
    }
    
    public void notifyRejectRegistration(User user, String reason) {
    
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                    user.getEmail(),
                    from,
                    String.format(
                            REJECT_REGISTRATION,
                            user.getOwner().getTitle(),
                            user.getUsername(),
                            LocalDateTime.now().format(DATE_FORMATTER),
                            reason,
                            URL
                    )
            );
    }
    
    public void rejectCard(User user, String reason) {
    
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                    user.getEmail(),
                    from,
                    String.format(
                            REJECT_CARD,
                            user.getOwner().getTitle(),
                            user.getUsername(),
                            LocalDateTime.now().format(DATE_FORMATTER),
                            reason
                    )
            );
    }
    
    public void rejectRequest(User user, String reason) {
        
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                    user.getEmail(),
                    from,
                    String.format(
                            REJECT_REQUEST,
                            user.getOwner().getTitle(),
                            user.getUsername(),
                            LocalDateTime.now().format(DATE_FORMATTER),
                            reason
                    )
            );
    }
    
    public void applyRequest(User user) {
    
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                    user.getEmail(),
                    from,
                    String.format(
                            APPLY_REQUEST,
                            user.getOwner().getTitle(),
                            user.getUsername(),
                            LocalDateTime.now().format(DATE_FORMATTER)
                    )
            );
    }
    
    public void applyCard(User user) {
        
        if(!StringUtils.isEmpty(user.getEmail()))
            send(
                    user.getEmail(),
                    from,
                    String.format(
                            APPLY_CARD,
                            user.getOwner().getTitle(),
                            user.getUsername(),
                            LocalDateTime.now().format(DATE_FORMATTER)
                    )
            );
    }
}
