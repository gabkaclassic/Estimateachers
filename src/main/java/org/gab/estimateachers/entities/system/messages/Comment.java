package org.gab.estimateachers.entities.system.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.users.User;

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
    private Double rating;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private User author;
}
