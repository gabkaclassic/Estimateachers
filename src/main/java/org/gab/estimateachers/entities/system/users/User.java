package org.gab.estimateachers.entities.system.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Value("${upload.filename.default.people}")
    private static String upload;
    
    @Value("${upload.path}")
    private static String defaultFilename;
    
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
    
    @Column(name = "applied", columnDefinition = "boolean default 'f'")
    private boolean applied;
    
    @Column(name = "activation_code")
    private String activationCode;
    
    @Column(name = "online", columnDefinition = "boolean default 'f'")
    private boolean online;
    
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
    
    public User() {
        
        setFilename(upload + defaultFilename);
    }
    
    public User(String username, String password, String email) {
        
        this();
        
        setUsername(username);
        setEmail((Objects.nonNull(email) && email.isEmpty()) ? null : email);
        setActive(true);
        setOnline(false);
        setApplied(false);
        setPassword(password);
        roles.add(Roles.LOCKED);
    }
    
    public void apply() {
        
        setApplied(true);
        unlock();
    }
    
    public boolean equals(Object other) {
        
        if(Objects.nonNull(other) && other instanceof User) {
            
            User user = (User)other;
            return user.getId().equals(getId());
        }
        
        return false;
    }
    
    public int hashCode() {
        
        return getId().intValue();
    }
    
    public void unlock() {
    
        if(Objects.isNull(activationCode) && applied) {
            roles.remove(Roles.LOCKED);
            roles.add(Roles.USER);
        }
    }
    
    public void appointmentAdmin() {
        
        roles.add(Roles.ADMIN);
    }
    
    public void deprivePower() {
        
        roles.remove(Roles.ADMIN);
    }
    
    public void lock() {
        
        roles.add(Roles.LOCKED);
        roles.remove(Roles.USER);
        roles.remove(Roles.ADMIN);
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
    
    public boolean isUser() {
        
        return roles.contains(Roles.USER);
    }
    
    public boolean isLocked() {
        
        return roles.contains(Roles.LOCKED);
    }
    
    public boolean online() {
        
        return online;
    }
    
    public void confirmEmail() {
    
        setActivationCode(null);
        unlock();
    }
    
    public void setActivationCode(String code) {
        
        activationCode = code;
    }
}
