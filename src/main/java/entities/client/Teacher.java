package entities.client;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
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
    
    @Column(name = "age")
    private int age;
    
    @Column(name = "photo")
    @Type(type = "org.hibernate.type.BlobType")
    private BufferedImage photo;
    
    @Column(name = "total_rating")
    private double totalRating;
    
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
    @Enumerated(EnumType.STRING)
    private Set<String> excuses;
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Student.class,
            mappedBy = "teachers"
    )
    private Set<Student> students;
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Faculty.class,
            mappedBy = "teachers"
    )
    private Set<Faculty> faculties;
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = University.class,
            mappedBy = "teachers"
    )
    private Set<University> universities;
    
}
