package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    
    @Column(
            name = "title",
            nullable = false,
            unique = true
    )
    protected String title;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    protected Set<String> photos;
    
    public Card(String title) {
        
        setTitle(title);
        photos = new HashSet<>();
    }
    
    public void addPhoto(String filename) {
        
        photos.add(filename);
    }
    
    protected abstract Double getTotalRating();
}
