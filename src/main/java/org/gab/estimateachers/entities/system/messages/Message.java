package org.gab.estimateachers.entities.system.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.messages.Chat;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;
}
