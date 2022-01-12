package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gab.estimateachers.entities.system.estimations.Estimation;
import org.gab.estimateachers.entities.system.estimations.FacultyEstimation;
import org.gab.estimateachers.entities.system.estimations.TeacherEstimation;
import org.gab.estimateachers.entities.system.estimations.UniversityEstimation;
import org.gab.estimateachers.entities.system.users.User;
import org.junit.gen5.commons.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends Card {
    
    private static final String FORMAT_TITLE = "%s %c.%c";
    
    @Column(
            name = "first_name",
            nullable = false
    )
    @Getter
    @Setter
    private String firstName;
    
    @Column(
            name = "last_name",
            nullable = false
    )
    @Getter
    @Setter
    private String lastName;
    
    @Column(
            name = "patronymic",
            nullable = false
    )
    @Getter
    @Setter
    private String patronymic;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    @Getter
    @Setter
    private Set<String> excuses = new HashSet<>();
    
    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            mappedBy = "teachers"
    )
    @Getter
    @Setter
    private Set<Faculty> faculties = new HashSet<>();
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            mappedBy = "teachers"
    )
    @Getter
    @Setter
    private Set<University> universities = new HashSet<>();
    
    @OneToMany
    @JoinTable(
            name = "teachers_estimations",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "estimation_id")
    
    )
    private List<TeacherEstimation> estimations;
    
    
    public Teacher(String firstName, String lastName, String patronymic) {
        
        super(String.format(FORMAT_TITLE,
                lastName,
                firstName.toUpperCase().charAt(0),
                Objects.isNull(patronymic) || patronymic.length() == 0 ? ' ' : patronymic.toUpperCase().charAt(0)
        ));
        
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
    }
    
    public void addUniversity(University university) {
        
        universities.add(university);
    }
    
    public void addFaculty(Faculty faculty) {
        
        faculties.add(faculty);
    }
    
    public String getTitle() {
    
        return String.format(FORMAT_TITLE,
                getLastName(),
                getFirstName().toUpperCase().charAt(0),
                Objects.isNull(patronymic) || patronymic.length() == 0 ? '-' : getPatronymic().toUpperCase().charAt(0)
        );
    }
    
    public Double getExactingRating() {
        
        return estimations.stream().map(TeacherEstimation.class::cast).mapToDouble(TeacherEstimation::getExactingRating).average().orElse(0);
    }
    
    public Double getFreebiesRating() {
        
        return estimations.stream().map(TeacherEstimation.class::cast).mapToDouble(TeacherEstimation::getFreebiesRating).average().orElse(0);
    }
    
    public Double getSeverityRating() {
        
        return estimations.stream().map(TeacherEstimation.class::cast).mapToDouble(TeacherEstimation::getSeverityRating).average().orElse(0);
    }
    
    public void estimation(TeacherEstimation estimation) {
        
        estimations.add(estimation);
    }
    
    public Double getTotalRating() {
        
        return round(estimations.stream().mapToDouble(TeacherEstimation::getTotalRating).average().orElse(0));
    }
    
    public Integer getAssessorsCount() {
        
        return estimations.size();
    }
    
    public boolean containsAssessor(User user) {
        
        return estimations.stream().map(TeacherEstimation::getAssessor).collect(Collectors.toSet()).contains(user);
    }
}
