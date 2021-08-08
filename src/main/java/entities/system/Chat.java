package entities.system;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "chats")
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "chats"
    )
    private Set<User> participants;
    
    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Message.class
    )
    private Set<Message> messages;
    
}
