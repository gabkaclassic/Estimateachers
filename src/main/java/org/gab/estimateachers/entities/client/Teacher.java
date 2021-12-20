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
    
    private static final String FORMAT_TITLE = "%s %c.%c";
    
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
    
    @Column(
            name = "severity_rating",
            columnDefinition = "float8 default 0.0"
    )
    private double severityRating;
    
    @Column(
            name = "exacting_rating",
            columnDefinition = "float8 default 0.0"
    )
    private double exactingRating;
    
    @Column(
            name = "freebies_rating",
            columnDefinition = "float8 default 0.0"
    )
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
        setExactingRating(0.0);
        setSeverityRating(0.0);
        setFreebiesRating(0.0);
        setTotalRating(0.0);
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
                getPatronymic().toUpperCase().charAt(0)
        );
    }
    
    protected Double getTotalRating() {
        
        return totalRating = (severityRating + exactingRating + freebiesRating) / 3;
    }
}
