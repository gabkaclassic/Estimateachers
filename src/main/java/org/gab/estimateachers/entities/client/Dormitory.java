package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dormitories")
public class Dormitory extends Card {
    
    @Column(name = "cleaning_rating")
    private Double cleaningRating;
    
    @Column(name = "roommates_rating")
    private Double roommatesRating;
    
    @Column(name = "capacity_rating")
    private Double capacityRating;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private University university;
    
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Student> students = new HashSet<>();
    
    public Dormitory(String title, University university) {
        
        super(title);
        
        setUniversity(university);
    }
    
    public Double getTotalRating() {
        
        return (cleaningRating + roommatesRating + capacityRating) / 3;
    }
}