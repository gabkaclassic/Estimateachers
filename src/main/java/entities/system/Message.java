package entities.system;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = User.class
    )
    private User author;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Chat.class
    )
    private Chat chat;

}
