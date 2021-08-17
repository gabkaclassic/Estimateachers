package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

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
    
    @Column(
            name = "course",
            nullable = false
    )
    private int course;
    
    @Column(name = "photo")
    @Type(type = "org.hibernate.type.BlobType")
    private BufferedImage photo;
    
    @ManyToOne(
            targetEntity = University.class,
            cascade = CascadeType.ALL
    )
    private University university;
    
    @ManyToOne(
            targetEntity = Faculty.class,
            cascade = CascadeType.ALL
    )
    private Faculty faculty;
    
    @ManyToMany(
            targetEntity = Teacher.class,
            cascade = CascadeType.ALL,
            mappedBy = "students",
            fetch = FetchType.LAZY
    )
    private Set<Teacher> teachers = new HashSet<>();;
    
    @OneToOne(
            cascade = CascadeType.ALL,
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private User account;
    
    @ManyToOne(
            targetEntity = Dormitory.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Dormitory dormitory;
    
}
