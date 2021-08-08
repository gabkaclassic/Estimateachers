package entities.client;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
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
            mappedBy = "faculties",
            targetEntity = Teacher.class
    )
    private Set<Teacher> teachers;
    
    @OneToMany(
            orphanRemoval = false,
            cascade = CascadeType.ALL,
            targetEntity = Student.class,
            fetch = FetchType.EAGER
    )
    private Set<Student> students;
    
}
