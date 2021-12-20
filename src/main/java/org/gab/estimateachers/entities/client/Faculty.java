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
    
    @Column(
            name = "price_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    private Double priceRating;
    
    @Column(
            name = "education_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    private Double educationRating;

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
    @Getter
    @Setter
    private University university;

    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "faculties_teachers",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    @Getter
    @Setter
    private Set<Teacher> teachers;

    public Faculty(String title, University university) {
        
        setTitle(String.format("%s(%s)", title.substring(0, 1).toUpperCase().concat(title.substring(1)), university.getAbbreviation()));
        photos = new HashSet<>();
        setEducationRating(0.0);
        setPriceRating(0.0);
        setTotalRating(0.0);
        setUniversity(university);
    }
    
    public Double getTotalRating() {
        
        return totalRating = (priceRating + educationRating) / 2;
    }
    
    public void addTeacher(Teacher teacher) {
    
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
}
