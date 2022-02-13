package org.gab.estimateachers.entities.system.discussions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Slf4j
@Data
@Entity
@Table(name = "comments")
public class Comment {
    
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yy hh:mm:ss");
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    protected User author;
    
     @Column(name = "text")
    protected String text;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    
    @Column(name = "rating", columnDefinition = "int8 default 0")
    private Integer rating;
    
    @ManyToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private Discussion discussion;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> likers;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> dislikers;
    
    public Comment() {
        
        setRating(0);
    }
    
    public Comment(String text, String date, User author) {
        
        this();
        
        setText(text);
        setAuthor(author);
        setDate(date);
    }
    
    public void like(User user) {
        
        if(!likers.contains(user)) {
            
            setRating(getRating() + 1);
            likers.add(user);
            if(dislikers.contains(user)) {
                setRating(getRating() + 1);
                dislikers.remove(user);
            }
        }
    }
    
    public void dislike(User user) {
    
        if(!dislikers.contains(user)) {
            
            setRating(getRating() - 1);
            dislikers.add(user);
            if(likers.contains(user)) {
                setRating(getRating() - 1);
                likers.remove(user);
            }
        }
    }
    
    private void setDate(String date) {
        
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            log.warn(String.format("Incorrect parsing date: exception. Exception: %s, reason: %s, stack trace: %s",
                    e.getMessage(), e.getCause(), Arrays.toString(e.getStackTrace())));
        }
    }
    
    public String getDate() {
        
        return dateFormat.format(date);
    }
}
