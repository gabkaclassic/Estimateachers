package org.gab.estimateachers.app.utilities;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component("applicationsUtilities")
public class ApplicationsUtilities {
    
    private static final Pattern REQUEST_TEXT_PATTERN = Pattern.compile("[a-zA-zА-Яа-я]+");
    
    public void checkRequestData(String text, String type, List<String> remarks) {
        
        boolean isCorrectText = Objects.nonNull(text)
                && !(text=text.trim()).isEmpty()
                && REQUEST_TEXT_PATTERN.matcher(text).find();
        
        if(!isCorrectText)
            remarks.add("Enter your request");
        if(Objects.isNull(type))
            remarks.add("Select type of your request");
    }
}
