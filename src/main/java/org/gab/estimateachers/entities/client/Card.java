package org.gab.estimateachers.entities.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
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
            name = "card_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    protected CardType cardType;
    
    @Column(
            name = "approved",
            columnDefinition = "boolean default 'f'"
    )
    protected Boolean approved;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    protected Set<String> photos;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discussion_id")
    protected Discussion discussion;
    
    public Card(String title) {
    
        setApproved(false);
        setTitle(title);
        photos = new HashSet<>();
    }
    
    public void addPhoto(String filename) {
        
        if(Objects.nonNull(filename) && !filename.isEmpty())
            photos.add(filename);
    }
    
    protected Double round(Double value) {
        
        return (double)(Math.round(value*100)/100);
    }
    
    public abstract Double getTotalRating();
    
    public abstract Integer getAssessorsCount();
    
    public abstract boolean containsAssessor(User user);
    
    public String getCardType() {
        
        return cardType.name();
    }
    
}