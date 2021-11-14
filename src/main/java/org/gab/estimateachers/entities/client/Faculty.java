package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "faculties")
public class Faculty {
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Getter
    @Setter
    @Column(
            name = "title",
            nullable = false
    )
    private String title;
    
    @Getter
    @Column(name = "total_rating")
    private Double totalRating;
    
    @Getter
    @Column(name = "price_rating")
    private Double priceRating;
    
    @Getter
    @Column(name = "education_rating")
    private Double educationRating;
    
    @Getter
    @Setter
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private University university;
    
    @Getter
    @Setter
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Teacher> teachers;
    
    @Getter
    @Setter
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Student> students = new HashSet<>();

    public Faculty(String title, University university){
        
        this.title = title;
        this.university = university;
    }
    
}
