package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(
            name = "username",
            unique = true,
            nullable = false
    )
    private String username;
    
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    
    @Column(
            name = "email",
            unique = true
    )
    private String email;
    
    @Column(
            name = "photo",
            unique = true
    )
    private String filename;
    
    @Column(name = "active")
    private boolean active;
    
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Student owner;
    
    @ElementCollection(
            fetch = FetchType.EAGER,
            targetClass = Roles.class
    )
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();
    
    @ManyToMany(
          cascade = CascadeType.PERSIST,
          fetch = FetchType.EAGER,
          mappedBy = "participants"
    )
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<Message> messages = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();
    
    public User(String name, String email, String password) {
        
        username = name;
        this.email = (Objects.nonNull(email) && email.isEmpty()) ? null : email;
        this.password = password;
        active = true;
        roles.add(Roles.LOCKED);
    }
    
    public void apply() {
        
        roles.remove(Roles.LOCKED);
        roles.add(Roles.USER);
    }
    
    public void appointmentAdmin() {
        
        roles.add(Roles.ADMIN);
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return getRoles();
    }
    
    public String getUsername() {
        
        return username;
    }
    
    public boolean isAccountNonExpired() {
        
        return true;
    }
    
    public boolean isAccountNonLocked() {
        
        return !roles.contains(Roles.LOCKED);
    }
    
    public boolean isCredentialsNonExpired() {
        
        return true;
    }
    
    public boolean isEnabled() {
        
        return isActive();
    }
    
    public boolean isAdmin() {
        
        return roles.contains(Roles.ADMIN);
    }
}
