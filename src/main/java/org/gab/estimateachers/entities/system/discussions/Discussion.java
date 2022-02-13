package org.gab.estimateachers.entities.system.discussions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Card;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "discussions")
public class Discussion {
    
    private static final String TITLE_PATTERN = "Discussion of the card: %s";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            mappedBy = "discussion"
    )
    private List<Comment> comments;
    
    public Discussion(Card card) {
        
        setTitle(String.format(TITLE_PATTERN, card.getTitle()));
        setComments(Collections.emptyList());
    }
    
    public void addComment(Comment comment) {
        
        comments.add(comment);
    }
}
