package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "universities")
public class University {
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Getter
    @Setter
    @Column(
            name = "title",
            nullable = false,
            unique = true
    )
    private String title;
    
    @Getter
    @Setter
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Getter
    @Setter
    @Column(name = "total_rating")
    private Double totalRating;
    
    @Getter
    @Setter
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    private Set<String> photos;
    
    @Getter
    @Setter
    @OneToMany(
            orphanRemoval = true,
            targetEntity = Faculty.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Faculty> faculties = new HashSet<>();
    
    @Getter
    @Setter
    @ManyToMany(
            targetEntity = Teacher.class,
            cascade = CascadeType.ALL
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @Getter
    @Setter
    @OneToMany(
            targetEntity = Student.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Student> students = new HashSet<>();
    
    @Getter
    @Setter
    @OneToMany(
            targetEntity = Dormitory.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Dormitory> dormitories = new HashSet<>();
    
    public University(String title) {
        
        StringBuilder builder = new StringBuilder();
        
        for(String word: title.split(" "))
            builder.append(word.substring(0, 1).toUpperCase());
    
        this.title = title;
        abbreviation = builder.toString();
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
}
