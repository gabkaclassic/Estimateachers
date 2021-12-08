package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
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
    
    @Column(name = "email")
    private String email;
    
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
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            mappedBy = "teachers"
    )
    private Set<Faculty> faculties = new HashSet<>();
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinTable(
            name = "universities_teachers",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private Set<University> universities = new HashSet<>();
    
    public Teacher(String firstName, String lastName, String patronymic, String email) {
        
        super(lastName +
                ' ' +
                firstName.charAt(0) +
                '.' +
                patronymic.charAt(0) +
                '.');
        
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setEmail(email);
    }
    
    public void addUniversity(University university) {
        
        universities.add(university);
    }
    
    public void addFaculty(Faculty faculty) {
        
        faculties.add(faculty);
    }
    
    public String getTitle() {
        
        return lastName.concat(" ").concat(firstName);
    }
    
    protected Double getTotalRating() {
        
        return (severityRating + exactingRating + freebiesRating) / 3;
    }
}
