package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gab.estimateachers.entities.system.users.Genders;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(
        name = "students",
        uniqueConstraints = @UniqueConstraint(name = "unique_account", columnNames = "account_id")
)
public class Student {
    
    private static final String FORMAT_TITLE = "%s %c.%c";
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Getter
    @Setter
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    
    @Getter
    @Setter
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    
    @Getter
    @Setter
    @Column(
            name = "patronymic",
            nullable = false
    )
    private String patronymic;
    
    @Getter
    @Setter
    @Column(
            name = "gender",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Genders gender;
    
    @Getter
    @Setter
    @Column(
            name = "course",
            nullable = false
    )
    private Integer course = 1;
    
    @Getter
    @Setter
    @OneToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private User account;
    
    @Getter
    @Setter
    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "university_students",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private University university;
    
    @Getter
    @Setter
    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "faculty_students",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "faculty_id")}
    )
    private Faculty faculty;
    
    @Getter
    @Setter
    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    private Set<Teacher> teachers = new HashSet<>();
    
    @Getter
    @Setter
    @ManyToOne(
            cascade = CascadeType.PERSIST,
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
    
    public String getTitle() {
    
        return String.format(FORMAT_TITLE,
                getLastName(),
                getFirstName().toUpperCase().charAt(0),
                Objects.isNull(patronymic) || patronymic.length() == 0 ? '-' : getPatronymic().toUpperCase().charAt(0)
        );
    }
    
    public void addTeacher(Teacher teacher) {
        
        teachers.add(teacher);
    }
}
