package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends Card {
    
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    
    @Column(
            name = "patronymic",
            nullable = false
    )
    private String patronymic;
    
    @Column(name = "age")
    private int age;
    
    @Column(name = "severity_rating")
    private double severityRating;
    
    @Column(name = "exacting_rating")
    private double exactingRating;
    
    @Column(name = "freebies_rating")
    private double freebiesRating;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    private Set<String> excuses = new HashSet<>();
    
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = Faculty.class,
            mappedBy = "teachers"
    )
    private Set<Faculty> faculties = new HashSet<>();
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = University.class,
            mappedBy = "teachers"
    )
    private Set<University> universities = new HashSet<>();
    
    public Teacher(String firstName, String lastName, String patronymic) {
        
        super(lastName +
                ' ' +
                firstName.charAt(0) +
                '.' +
                patronymic.charAt(0) +
                '.');
        
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
    }
    
    public String getTitle() {
        
        return lastName.concat(" ").concat(firstName);
    }
    
    protected Double getTotalRating() {
        
        return (severityRating + exactingRating + freebiesRating) / 3;
    }
}
