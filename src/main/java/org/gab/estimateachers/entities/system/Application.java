package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(
            name = "card_photo",
            nullable = false
    )
    private String filename;
    
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Student student;
    
    public Application(String filename, Student student) {
        
        this.filename = filename;
        this.student = student;
    }
}
