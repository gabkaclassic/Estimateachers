package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.Genders;
import org.gab.estimateachers.entities.system.User;

import javax.persistence.*;
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
            name = "patronymic",
            nullable = false
    )
    private String patronymic;
    
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
    @JoinTable(
            name = "university_students",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private University university;
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "faculty_students",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "faculty_id")}
    )
    private Faculty faculty;
    
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "dormitory_students",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "dormitory_id")}
    )
    private Dormitory dormitory;
    
    public Student(String firstName, String lastName, String patronymic, Genders gender, User account) {
        
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setGender(gender);
        setAccount(account);
    }
}
