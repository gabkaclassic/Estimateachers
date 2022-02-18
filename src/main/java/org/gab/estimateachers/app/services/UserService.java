package org.gab.estimateachers.app.services;

import io.sentry.spring.tracing.SentrySpan;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service("userService")
public class UserService implements UserDetailsService  {
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    @Qualifier("mailService")
    private MailService mailService;
    
    public List<User> findAll() {
       
        return userRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        userRepository.deleteById(id);
    }
    
    public User createUser(String username, String password, String email) {
        
        password = passwordEncoder.encode(password);
        
        return new User(username, password, email);
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        if(Objects.isNull(username)) {
            
            NullPointerException exception = new NullPointerException("Login is null");
            
            log.warn(String.format("The null value was passed instead of the user's nickname. Exception: %s, reason: %s, stack trace: %s",
                    exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
            throw exception;
        }
        
        User user = userRepository.findByUsername(username);
        
        if(Objects.isNull(user)) {
    
            NullPointerException exception = new NullPointerException("User not found");
    
            log.warn(String.format("The user was not found by nickname. Exception: %s, reason: %s, stack trace: %s",
                    exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
            
            throw exception;
        }
        return user;
    }
    
    @SentrySpan
    public User findById(Long id) {
        
        return userRepository.findById(id).orElse(null);
    }
    
    public void save(User user) {
        
        userRepository.saveAndFlush(user);
    }
    
    public List<User> findByLoginPattern(String pattern) {
        
        return userRepository.findByUsernamePattern(pattern);
    }
    
    public void update(Long id, String username, String password, String email) {
        
        User user = userRepository.getOne(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        
        if(!user.isAdmin() && Objects.nonNull(user.getEmail()) && Objects.nonNull(email) && !user.getEmail().equals(email)) {
            user.lock();
            user.setActivationCode(UUID.randomUUID().toString());
            mailService.sendConfirmEmail(user);
        }
    
        user.setEmail(email);
        
        userRepository.save(user);
    }
    public void update(Long id, String username, String password, String email, MultipartFile profilePhoto) {
    
        User user = userRepository.getOne(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
    
        user.setEmail(email);
        user.setFilename(filesUtilities.registrationFile(profilePhoto, RegistrationType.PEOPLE));
        
        if(!user.isAdmin() && Objects.nonNull(user.getEmail()) && Objects.nonNull(email) && !user.getEmail().equals(email)) {
            user.lock();
            user.setActivationCode(UUID.randomUUID().toString());
            mailService.sendConfirmEmail(user);
        }
        
        userRepository.saveAndFlush(user);
    }
    
    public void createAdmin(String login, String password) {
        
        password = passwordEncoder.encode(password);
        
        User user = new User(login, password, Strings.EMPTY);
        user.appointmentAdmin();
        user.apply();
        
        userRepository.save(user);
    }
    
    public boolean confirmEmail(String activationCode) {
        
        User user = userRepository.findByActivationCode(activationCode);
        boolean isNonNullUser = Objects.nonNull(user);
        if(isNonNullUser) {
            user.confirmEmail();
            userRepository.save(user);
        }
        
        return isNonNullUser;
    }
}

