package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Teacher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher_cards")
public class TeacherCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToMany(
            targetEntity = Comment.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "card"
    )
    private Set<Comment> comments = new HashSet<>();
    
    @OneToOne(
            targetEntity = Teacher.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Teacher teacher;
    
}
    
