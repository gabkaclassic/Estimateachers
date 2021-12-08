package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.repositories.system.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Component("usersUtilities")
public class UsersUtilities {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+\\.\\w+");
    private static final Pattern PASSWORD_PATTERN_FIRST = Pattern.compile("[a-zA-Z]+");
    private static final Pattern PASSWORD_PATTERN_SECOND = Pattern.compile("[0-9]+");
    private static final Pattern PASSWORD_PATTERN_THREE = Pattern.compile("[^0-9a-zA-Z]+");
    private static final Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-z]+");
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-zА-Яа-я]+");
    
    private static final int MIN_LENGTH_PASSWORD = 8;
    private static final int MAX_LENGTH_PASSWORD = 32;
    private static final int MIN_LENGTH_LOGIN = 2;
    private static final int MAX_LENGTH_LOGIN = 32;
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    public boolean checkUserDataFromRegistration(String firstName,
                                 String lastName,
                                 String patronymic,
                                 String login,
                                 String password,
                                 String email,
                                 MultipartFile cardPhoto,
                                 List<String> remarks) {

        return checkNames(firstName, lastName, patronymic, remarks)
                & checkLogin(login, remarks)
                & checkPassword(password, remarks)
                & checkEmail(email, remarks)
                & checkFile(cardPhoto, remarks);
    }
    
    public boolean checkNames(String firstName, String lastName, String patronymic, List<String> remarks) {
        
        boolean isCorrectNames = (Objects.nonNull(firstName) && (!firstName.isEmpty())
                && ((firstName = firstName.substring(0, 1).toUpperCase(Locale.ROOT).concat(firstName.substring(1)).trim()))
                .length() >= MIN_LENGTH_LOGIN && firstName.length() <= MAX_LENGTH_LOGIN)
                && NAME_PATTERN.matcher(firstName).matches()
                && (Objects.nonNull(lastName) && (!lastName.isEmpty())
                && ((lastName = lastName.substring(0, 1).toUpperCase(Locale.ROOT).concat(lastName.substring(1)).trim()))
                .length() >= MIN_LENGTH_LOGIN && lastName.length() <= MAX_LENGTH_LOGIN)
                && NAME_PATTERN.matcher(lastName).matches()
                && (Objects.nonNull(patronymic) && (!patronymic.isEmpty())
                && ((patronymic = patronymic.substring(0, 1).toUpperCase(Locale.ROOT).concat(patronymic.substring(1)).trim()))
                .length() >= MIN_LENGTH_LOGIN && patronymic.length() <= MAX_LENGTH_LOGIN)
                && NAME_PATTERN.matcher(patronymic).matches();
                
        if(!isCorrectNames)
            remarks.add(String.format("The first, last name and patronymic must consist of %d-%d letters", MIN_LENGTH_LOGIN, MAX_LENGTH_LOGIN));
        
        return isCorrectNames;
    }
    
    public boolean checkPassword(String password, List<String> remarks) {
        
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
           remarks.add(String.format("The password must contain %d-%d characters, of which 1 digit and 1 specified character", MIN_LENGTH_PASSWORD, MAX_LENGTH_PASSWORD));
       
       return isCorrectPassword;
    }
    
    public boolean checkLogin(String login, List<String> remarks) {
        
       boolean isCorrectLogin = checkLoginWithoutUnique(login, remarks);
    
        if(!isCorrectLogin)
            remarks.add(String.format("The login can contain %d-%d characters and 1 letter", MIN_LENGTH_LOGIN, MAX_LENGTH_LOGIN));
        
        if(userRepository.existsByUsername(login)) {
            
            remarks.add("Entered login is already in use");
            isCorrectLogin = false;
        }
            
            return isCorrectLogin;
        }
    
    public boolean checkEmail(String email, List<String> remarks) {
        
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
    
    public boolean checkFile(MultipartFile file, List<String> remarks) {
        
        boolean isCorrectFile = Objects.nonNull(file) && !file.isEmpty();
        
        if(!isCorrectFile)
            remarks.add("A photo of your student card is required");
        
        return isCorrectFile;
    }
    
    public boolean checkLoginWithoutUnique(String login, List<String> remarks) {
    
        boolean isCorrectLogin = Objects.nonNull(login)
                && LOGIN_PATTERN.matcher((login = login.trim())).find()
                && (login.length() >= MIN_LENGTH_LOGIN)
                && (login.length() <= MAX_LENGTH_LOGIN);
    
        if(!isCorrectLogin)
            remarks.add(String.format("The login can contain %d-%d characters and 1 letter", MIN_LENGTH_LOGIN, MAX_LENGTH_LOGIN));
        
        return isCorrectLogin;
    }
}
