package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
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
    private Set<String> excuses = new HashSet<>();;
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Student.class
    )
    private Set<Student> students = new HashSet<>();
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Faculty.class,
            mappedBy = "teachers"
    )
    private Set<Faculty> faculties = new HashSet<>();;
    
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = University.class,
            mappedBy = "teachers"
    )
    private Set<University> universities = new HashSet<>();;
    
}
