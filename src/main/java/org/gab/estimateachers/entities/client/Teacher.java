package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.gen5.commons.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    
    @Column(
            name = "severity_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    private double severityRating;
    
    @Column(
            name = "exacting_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    private double exactingRating;
    
    @Column(
            name = "freebies_rating",
            columnDefinition = "float8 default 0.0"
    )
    @Getter
    @Setter
    private double freebiesRating;
    
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
    
    public Teacher(String firstName, String lastName, String patronymic) {
        
        super(String.format(FORMAT_TITLE,
                lastName,
                firstName.toUpperCase().charAt(0),
                Objects.isNull(patronymic) || patronymic.length() == 0 ? ' ' : patronymic.toUpperCase().charAt(0)
        ));
        
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
                Objects.isNull(patronymic) || patronymic.length() == 0 ? '-' : getPatronymic().toUpperCase().charAt(0)
        );
    }
    
    protected Double getTotalRating() {
        
        return totalRating = (severityRating + exactingRating + freebiesRating) / 3;
    }
}
