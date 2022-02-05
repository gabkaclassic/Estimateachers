package org.gab.estimateachers.app.utilities;

import org.gab.estimateachers.app.services.CardService;
import org.gab.estimateachers.app.services.Service;
import org.gab.estimateachers.entities.client.Card;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Component("cardsUtilities")
public class CardsUtilities {
    
    private static final Pattern TITLE_PATTERN = Pattern.compile("[a-zA-zА-Яа-я]+");
    private static final int MIN_LENGTH_TITLE = 2;
    private static final int MAX_LENGTH_TITLE = 128;
    
    public boolean checkTitle(String title, List<String> remarks, CardService<? extends Card> service) {
        
        boolean isCorrectTitle = Objects.nonNull(title)
                && TITLE_PATTERN.matcher(
                        title = (title.substring(0, 1).toUpperCase(Locale.ROOT).concat(title.substring(1)).trim())
                ).find()
                && (title.length() >= MIN_LENGTH_TITLE) && (title.length() <= MAX_LENGTH_TITLE);
        
        if(!isCorrectTitle)
            remarks.add(String.format("The title can contain %d-%d characters and 1 letter", MIN_LENGTH_TITLE, MAX_LENGTH_TITLE));
    
        if(remarks.isEmpty() && service.existsByTitle(title))
            remarks.add("This title already exists");
        
        
        return isCorrectTitle;
    }
}
