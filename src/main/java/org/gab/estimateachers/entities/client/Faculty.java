package org.gab.estimateachers.entities.client;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(
        name = "faculties",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
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
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @NonNull
    @JoinTable(
            name = "university_faculties",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private University university;
    
    @Getter
    @Setter
    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "faculties_teachers",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    private Set<Teacher> teachers;

    public Faculty(String title, University university) {
    
        setTitle(String.format("%s(%s)", title.substring(0, 1).toUpperCase().concat(title.substring(1)), university.getAbbreviation()));
        photos = new HashSet<>();
        educationRating = 0.0;
        priceRating = 0.0;
        setUniversity(university);
    }
    
    public Double getTotalRating() {
        
        if(Objects.isNull(priceRating) || Objects.isNull(educationRating)) {
            
            priceRating = 0.0;
            educationRating = 0.0;
            
            return 0.0;
        }
        
        return (priceRating + educationRating) / 2;
    }
    
    public void addTeacher(Teacher teacher) {
    
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
}
