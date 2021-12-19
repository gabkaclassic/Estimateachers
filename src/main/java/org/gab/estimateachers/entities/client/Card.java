package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@MappedSuperclass
public abstract class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    protected Long id;
    
    @Getter
    @Setter
    @Column(
            name = "title",
            nullable = false
    )
    protected String title;
    
    @Column(
            name = "approved",
            columnDefinition = "boolean default 'f'"
    )
    @Getter
    @Setter
    protected Boolean approved;
    
    @Column(
            name = "total_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    protected Double totalRating;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    @Getter
    @Setter
    protected Set<String> photos;
    
    public Card(String title) {
    
        setApproved(false);
        setTitle(title);
        photos = new HashSet<>();
    }
    
    public void addPhoto(String filename) {
        
        if(Objects.nonNull(filename) && !filename.isEmpty())
            photos.add(filename);
    }
    
    protected abstract Double getTotalRating();
}
