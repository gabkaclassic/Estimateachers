package entities.client;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.Set;

@Entity
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "rating")
    private double rating;
    
    @Column(name = "photos")
    @Type(type = "org.hibernate.type.BlobType")
    private Set<BufferedImage> photos;
    
    @OneToMany(
            orphanRemoval = true,
            targetEntity = Faculty.class,
            cascade = CascadeType.ALL,
            mappedBy = "university"
    )
    private Set<Faculty> faculties;
    
    @ManyToMany(
            targetEntity = Teacher.class,
            cascade = CascadeType.ALL,
            mappedBy = "universities"
    )
    private Set<Teacher> teachers;
    
    @OneToMany(
            targetEntity = Student.class,
            cascade = CascadeType.ALL,
            mappedBy = "university",
            fetch = FetchType.LAZY
    )
    private Set<Student> students;
    
    @OneToMany(
            targetEntity = Dormitory.class,
            cascade = CascadeType.ALL,
            mappedBy = "university",
            fetch = FetchType.LAZY
    )
    private Set<Dormitory> dormitories;
    
}
