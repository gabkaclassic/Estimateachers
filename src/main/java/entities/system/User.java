package entities.system;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @ElementCollection(
            fetch = FetchType.EAGER,
            targetClass = Roles.class
    )
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
    
    @ManyToMany(
          mappedBy = "participants",
          cascade = CascadeType.ALL,
          fetch = FetchType.LAZY,
          targetEntity = Chat.class
    )
    private Set<Chat> chats;
    
    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity = Message.class,
            cascade = CascadeType.ALL,
            mappedBy = "author"
    )
    private Set<Message> messages;
    
    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity = Comment.class,
            cascade = CascadeType.ALL,
            mappedBy = "author"
    )
    private Set<Comment> comments;
}
