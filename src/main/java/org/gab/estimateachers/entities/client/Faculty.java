package org.gab.estimateachers.entities.client;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "faculties")
public class Faculty extends Card {
    
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
    @NonNull
    private University university;
    
    @Getter
    @Setter
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "faculties_teachers",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    private Set<Teacher> teachers;
    
    @Getter
    @Setter
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Student> students = new HashSet<>();

    public Faculty(String title, University university) {
        
        super(title);
        
        setUniversity(university);
    }
    
    public Double getTotalRating() {
        
        return (priceRating + educationRating) / 2;
    }
}
