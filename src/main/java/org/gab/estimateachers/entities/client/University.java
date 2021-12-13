package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "universities")
public class University extends Card {
    
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(
            name = "price_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double priceRating;

    @Column(
            name = "complexity_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double complexityRating;

    @Column(
            name = "utility_rating",
            columnDefinition = "float8 default 0.0"
    )
    private Double utilityRating;

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
            fetch = FetchType.LAZY,
            mappedBy = "universities"
    )
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "university"
    )
    private Set<Dormitory> dormitories = new HashSet<>();
    
    public University(String title, Boolean bachelor, Boolean magistracy, Boolean specialty) {
        
        super(title);
        
        StringBuilder builder = new StringBuilder();
        
        for(String word: title.split(" "))
            builder.append(word.substring(0, 1).toUpperCase());
    
        abbreviation = builder.toString();
        setBachelor(Objects.nonNull(bachelor));
        setMagistracy(Objects.nonNull(magistracy));
        setSpecialty(Objects.nonNull(specialty));
        setComplexityRating(0.0);
        setPriceRating(0.0);
        setUtilityRating(0.0);
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
    
    protected Double getTotalRating() {
        
        return totalRating = (priceRating + utilityRating + complexityRating) / 3;
    }
    
    public void addTeacher(Teacher teacher) {
        
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
}
