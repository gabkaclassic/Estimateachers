package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    
    @Column(
            name = "title",
            nullable = false
    )
    protected String title;
    
    @Column(
            name = "approved",
            columnDefinition = "boolean default 'f'"
    )
    protected Boolean approved;
    
    @Column(
            name = "total_rating",
            columnDefinition = "float8 default 0.0"
    )
    protected Double totalRating;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    protected Set<String> photos;
    
    public Card(String title) {
    
        setApproved(false);
        setTitle(title);
        photos = new HashSet<>();
    }
    
    public void addPhoto(String filename) {
        
        photos.add(filename);
    }
    
    protected abstract Double getTotalRating();
}
