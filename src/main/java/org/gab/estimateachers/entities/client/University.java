package org.gab.estimateachers.entities.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "universities")
public class University extends Card {
    
    @Getter
    @Setter
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "price_rating")
    private Double priceRating;
    
    @Column(name = "complexity_rating")
    private Double complexityRating;
    
    @Column(name = "utility_rating")
    private Double utilityRating;
    
    @Getter
    @Setter
    @Column(name = "bachelor")
    private Boolean bachelor;
    
    @Getter
    @Setter
    @Column(name = "magistracy")
    private Boolean magistracy;
    
    @Getter
    @Setter
    @Column(name = "specialty")
    private Boolean specialty;
    
    @Getter
    @Setter
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            mappedBy = "university"
    )
    private Set<Faculty> faculties = new HashSet<>();
    
    @Getter
    @Setter
    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            mappedBy = "universities"
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @Getter
    @Setter
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
        
        return (priceRating + utilityRating + complexityRating) / 3;
    }
    
    public void addTeacher(Teacher teacher) {
        
        if(Objects.nonNull(teacher) && Objects.nonNull(teacher.getId()))
            teachers.add(teacher);
    }
}
