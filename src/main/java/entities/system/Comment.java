package entities.system;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
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
