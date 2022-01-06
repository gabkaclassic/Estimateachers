package org.gab.estimateachers.app.services;

import org.apache.logging.log4j.util.Strings;
import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserService implements UserDetailsService, org.gab.estimateachers.app.services.Service<User>  {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
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
        
        if(Objects.isNull(username))
            throw new NullPointerException("Login is null");
        
        User user = userRepository.findByUsername(username);
        
        if(Objects.isNull(user))
            throw new NullPointerException("User not found");
        
        return user;
    }
    
    public User findById(Long id) {
        
        return userRepository.findById(id).orElseThrow(NullPointerException::new); //TO DO
    }
    
    public void save(User user) {
        
        userRepository.saveAndFlush(user);
    }
    
    public List<User> findByLogin(String login) {
        
        return List.of(userRepository.findByUsername(login));
    }
    
    public List<User> findByLoginPattern(String pattern) {
        
        return userRepository.findByUsernamePattern(pattern);
    }
    
    public void update(Long id, String username, String password, String email) {
        
        User user = userRepository.getOne(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        
        user.setEmail(email); //TO DO
        
        userRepository.save(user);
    }
    public void update(Long id, String username, String password, String email, MultipartFile profilePhoto) {
        
        User user = userRepository.getOne(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFilename(filesUtilities.registrationFile(profilePhoto, RegistrationType.PEOPLE));
        
        user.setEmail(email); //TO DO
        
        userRepository.save(user);
    }
    
    public void createAdmin(String login, String password) {
        
        password = passwordEncoder.encode(password);
        
        User user = new User(login, password, Strings.EMPTY);
        user.appointmentAdmin();
        user.apply();
        
        userRepository.save(user);
    }
}

