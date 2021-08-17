package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<User> participants = new HashSet<>();;
    
    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Message.class
    )
    private Set<Message> messages = new HashSet<>();;
    
}
