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
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "rating")
    private double rating;
    
    @Column(name = "photos")
    @Type(type = "org.hibernate.type.BlobType")
    private Set<BufferedImage> photos = new HashSet<>();
    
    @OneToMany(
            orphanRemoval = true,
            targetEntity = Faculty.class,
            cascade = CascadeType.ALL,
            mappedBy = "university"
    )
    private Set<Faculty> faculties = new HashSet<>();
    
    @ManyToMany(
            targetEntity = Teacher.class,
            cascade = CascadeType.ALL
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @OneToMany(
            targetEntity = Student.class,
            cascade = CascadeType.ALL,
            mappedBy = "university",
            fetch = FetchType.LAZY
    )
    private Set<Student> students = new HashSet<>();
    
    @OneToMany(
            targetEntity = Dormitory.class,
            cascade = CascadeType.ALL,
            mappedBy = "university",
            fetch = FetchType.LAZY
    )
    private Set<Dormitory> dormitories = new HashSet<>();
    
}
