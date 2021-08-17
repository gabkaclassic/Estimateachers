package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "rating")
    private int rating;
    
    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private User author;
    
    @ManyToOne(
            targetEntity = TeacherCard.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private TeacherCard card;
    
}
