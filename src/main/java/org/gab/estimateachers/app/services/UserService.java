package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserService implements UserDetailsService, org.gab.estimateachers.app.services.Service<User>  {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
       
        return userRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        userRepository.deleteById(id);
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
    
    public void update(Long id, String username, String password, String email) {
        
        User user = userRepository.getOne(id);
        user.setUsername(username);
        user.setPassword(password);
        
        user.setEmail(email); //TO DO
        
        userRepository.save(user);
    }
}

