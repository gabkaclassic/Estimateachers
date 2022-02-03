package org.gab.estimateachers.entities.system.discussions;

import lombok.Data;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    protected User author;
    
    @Column(name = "text")
    protected String text;
    
    @Column(name = "rating", columnDefinition = "int8 default 0")
    private Integer rating;
    
    public Comment() {
        
        setRating(0);
    }
    
    public void like() {
        
        setRating(getRating()+1);
    }
    
    public void dislike() {
        
        setRating(getRating()-1);
    }
}
