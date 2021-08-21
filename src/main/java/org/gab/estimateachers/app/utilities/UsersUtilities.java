package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class UsersUtilities {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+\\.\\w+");
    private static final Pattern PASSWORD_PATTERN_FIRST = Pattern.compile("[a-zA-Z]+");
    private static final Pattern PASSWORD_PATTERN_SECOND = Pattern.compile("[0-9]+");
    private static final Pattern PASSWORD_PATTERN_THREE = Pattern.compile("[^0-9a-zA-Z]+");
    private static final Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-z]+");
    
    private static final int MIN_LENGTH_PASSWORD = 8;
    private static final int MAX_LENGTH_PASSWORD = 32;
    private static final int MIN_LENGTH_LOGIN = 2;
    private static final int MAX_LENGTH_LOGIN = 30;
    
//    @Autowired
//    private UserRepository userRepository;
    
    public static boolean checkUserData(String login, String password, String email, List<String> remarks, UserRepository userRepository) {

        return checkLogin(login, remarks, userRepository) & checkPassword(password, remarks) & checkEmail(email, remarks, userRepository);
    }
    
    private static boolean checkPassword(String password, List<String> remarks) {
        
       if(Objects.isNull(password)
               || (password = StringUtils.trimAllWhitespace(password)).isEmpty()) {
           
           remarks.add("Enter your password");
           
           return false;
       }
    
       boolean isCorrectPassword = (password.length() >= MIN_LENGTH_PASSWORD)
               && (password.length() <= MAX_LENGTH_PASSWORD)
               && PASSWORD_PATTERN_FIRST.matcher(password).find()
               && PASSWORD_PATTERN_SECOND.matcher(password).find()
               && PASSWORD_PATTERN_THREE.matcher(password).find();
       
       
       if(!isCorrectPassword)
           remarks.add("The password must contain from 8 to 32 characters, of which 1 digit and 1 specified character");
       
       return isCorrectPassword;
    }
    
    private static boolean checkLogin(String login, List<String> remarks, UserRepository userRepository) {
        
        if(Objects.isNull(login)
                || (login = StringUtils.trimAllWhitespace(login)).isEmpty()) {
    
            remarks.add("Enter your login");
            
            return false;
        }
    
        if(userRepository.existsByUsername(login))
            remarks.add("Entered login is already in use");
        
            boolean isCorrectLogin = (login.length() >= MIN_LENGTH_LOGIN)
                    && (login.length() <= MAX_LENGTH_LOGIN)
                    && LOGIN_PATTERN.matcher(login).find();
        
            if(!isCorrectLogin)
                remarks.add("The login can contain from 2 to 30 characters and 1 letter");
            
            return isCorrectLogin;
        }
    
    private static boolean checkEmail(String email, List<String> remarks, UserRepository userRepository) {
        
        boolean isCorrectEmailAddress = Objects.isNull(email)
                || email.isEmpty()
                || EMAIL_PATTERN.matcher(email).find();
        
        if(!isCorrectEmailAddress)
            remarks.add("Invalid email address");
        if(userRepository.existsByEmail(email)) {
            
            isCorrectEmailAddress = false;
            remarks.add("Entered email is already in use");
        }
        
        return isCorrectEmailAddress;
    }
}
