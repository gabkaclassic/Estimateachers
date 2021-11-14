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
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(
            name = "title",
            nullable = false,
            unique = true
    )
    private String title;
    
    @Column(name = "total_rating")
    private Double totalRating;
    
    @Column(name = "cleaning_rating")
    private Double cleaningRating;
    
    @Column(name = "roommates_rating")
    private Double roommatesRating;
    
    @Column(name = "capacity_rating")
    private Double capacityRating;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    private Set<String> photos;
    
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
        
        this.title = title;
        this.university = university;
    }
    
    public void addPhoto(String filename) {
        
        photos.add(filename);
    }
}
