package org.gab.estimateachers.entities.client;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "students",
        uniqueConstraints = @UniqueConstraint(name = "unique_account", columnNames = "account_id")
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    
    @Column(
            name = "gender",
            nullable = false
    )
    
    @Enumerated(EnumType.STRING)
    private Genders gender;
    
    @Column(
            name = "course",
            nullable = false
    )
    private Integer course = 1;
    
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private User account;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private University university;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Faculty faculty;
    
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Dormitory dormitory;
    
    public Student(String firstName, String lastName, Genders gender, User account) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
        this.gender = gender;
    }
}
