package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gab.estimateachers.entities.system.estimations.Estimation;
import org.gab.estimateachers.entities.system.estimations.UniversityEstimation;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "universities")
public class University extends Card {
    
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(
            name = "bachelor",
            columnDefinition = "boolean default 'f'"
    )
    private Boolean bachelor;

    @Column(
            name = "magistracy",
            columnDefinition = "boolean default 'f'"
    )
    private Boolean magistracy;

    @Column(
            name = "specialty",
            columnDefinition = "boolean default 'f'"
    )
    private Boolean specialty;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            mappedBy = "university"
    )
    private Set<Faculty> faculties = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "universities_teachers",
            joinColumns = {@JoinColumn(name = "university_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )

    private Set<Teacher> teachers = new HashSet<>();
    
    @OneToMany(targetEntity = UniversityEstimation.class)
    @JoinTable(
            name = "universities_estimations",
            joinColumns = @JoinColumn(name = "university_id"),
            inverseJoinColumns = @JoinColumn(name = "estimation_id")
    
    )
    private List<UniversityEstimation> estimations;
    
    @OneToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            mappedBy = "university"
    )
    private Set<Dormitory> dormitories = new HashSet<>();
    
    public University(String title, Boolean bachelor, Boolean magistracy, Boolean specialty) {
        
        super(title);
        
        StringBuilder builder = new StringBuilder();
        
        for(String word: title.split(" "))
            if(word.length() > 0)
                builder.append(word.substring(0, 1).toUpperCase());
    
        abbreviation = builder.toString();
        setBachelor(Objects.nonNull(bachelor));
        setMagistracy(Objects.nonNull(magistracy));
        setSpecialty(Objects.nonNull(specialty));
    }
    
    public void addDormitory(Dormitory dormitory) {
        
        dormitories.add(dormitory);
    }
    
    public void addFaculty(Faculty faculty) {
        
        faculties.add(faculty);
    }
    
    public void addPhoto(String filename) {
        
        photos.add(filename);
    }
    
    public Double getTotalRating() {
        
        return round(estimations.stream().mapToDouble(UniversityEstimation::getTotalRating).average().orElse(0));
    }
    
    public void addTeacher(Teacher teacher) {
        
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
    
    public Double getPriceRating() {
        
        return estimations.stream().map(UniversityEstimation.class::cast).mapToDouble(UniversityEstimation::getPriceRating).average().orElse(0);
    }
    
    public Double getComplexityRating() {
        
        return estimations.stream().map(UniversityEstimation.class::cast).mapToDouble(UniversityEstimation::getComplexityRating).average().orElse(0);
    }
    
    public Double getUtilityRating() {
        
        return estimations.stream().mapToDouble(UniversityEstimation::getUtilityRating).average().orElse(0);
    }
    
    public Integer getAssessorsCount() {
        
        return estimations.size();
    }
    
    public boolean containsAssessor(User user) {
        
        return estimations.stream().map(UniversityEstimation::getAssessor).collect(Collectors.toSet()).contains(user);
    }
    
    public void estimation(UniversityEstimation estimation) {
    
            estimations.add(estimation);
    }
}