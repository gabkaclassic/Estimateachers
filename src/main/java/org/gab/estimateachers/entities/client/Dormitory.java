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
    
    @Column(
            name = "cleaning_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double cleaningRating;
    
    @Column(
            name = "roommates_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double roommatesRating;
    
    @Column(
            name = "capacity_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double capacityRating;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "university_dormitories",
            joinColumns = {@JoinColumn(name = "dormitory_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private University university;
    
    public Dormitory(String title, University university) {
        
        super(title);
        setCapacityRating(0.0);
        setCleaningRating(0.0);
        setRoommatesRating(0.0);
        setTotalRating(0.0);
        setUniversity(university);
    }
    
    public Double getTotalRating() {
        
        return totalRating = (cleaningRating + roommatesRating + capacityRating) / 3;
    }
}