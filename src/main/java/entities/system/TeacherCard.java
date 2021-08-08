package entities.system;

import entities.client.Teacher;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teacher_cards")
public class TeacherCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @OneToMany(
            targetEntity = Comment.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "card"
    )
    private Set<Comment> comments;
    
    @OneToOne(
            targetEntity = Teacher.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Teacher teacher;
    
