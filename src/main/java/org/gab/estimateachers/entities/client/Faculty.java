package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = University.class
    )
    @JoinColumn(
            name = "university_id",
            nullable = false
    )
    private University university;
    
    @ManyToMany(
            cascade = CascadeType.ALL,
            targetEntity = Teacher.class
    )
    private Set<Teacher> teachers;
    
    @OneToMany(
            orphanRemoval = false,
            cascade = CascadeType.ALL,
            targetEntity = Student.class,
            fetch = FetchType.EAGER
    )
    private Set<Student> students = new HashSet<>();
    
}
