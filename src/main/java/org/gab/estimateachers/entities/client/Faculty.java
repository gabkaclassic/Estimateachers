package org.gab.estimateachers.entities.client;

import lombok.*;
import org.gab.estimateachers.entities.system.estimations.Estimation;
import org.gab.estimateachers.entities.system.estimations.FacultyEstimation;
import org.gab.estimateachers.entities.system.estimations.UniversityEstimation;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(
        name = "faculties",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class Faculty extends Card {

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
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
    
    @OneToMany
    @JoinTable(
            name = "faculties_estimations",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "estimation_id")
    
    )
    private List<FacultyEstimation> estimations;
    
    public Faculty(String title, University university) {
        
        setTitle(String.format("%s(%s)", title.substring(0, 1).toUpperCase().concat(title.substring(1)), university.getAbbreviation()));
        photos = new HashSet<>();
        setUniversity(university);
        setCardType(CardType.FACULTY);
    }
    
    public Double getPriceRating() {
        
        return estimations.stream().mapToDouble(FacultyEstimation::getPriceRating).average().orElse(0);
    }
    
    public Double getEducationRating() {
        
        return estimations.stream().mapToDouble(FacultyEstimation::getEducationRating).average().orElse(0);
    }
    
    public Double getTotalRating() {
        
        return estimations.stream().mapToDouble(FacultyEstimation::getTotalRating).average().orElse(0);
    }
    
    public Integer getAssessorsCount() {
        
        return estimations.size();
    }
    
    public boolean containsAssessor(User user) {
        
        return estimations.stream().map(FacultyEstimation::getAssessor).collect(Collectors.toSet()).contains(user);
    }
    
    public void estimation(FacultyEstimation estimation) {
        
        estimations.add(estimation);
    }
    
    public void addTeacher(Teacher teacher) {
    
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
}
