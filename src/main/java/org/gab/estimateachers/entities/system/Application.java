package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class Application {
    
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yy hh:mm");
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Student student;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    
    @Column(
            name = "viewed",
            nullable = false
    )
    private boolean viewed;
    
    protected Application(Student student, String date) {
        
        setViewed(false);
        setStudent(student);
        setDateSending(date);
    }
    
    private void setDateSending(String date) {
    
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            log.warn("Incorrect parsing date: ".concat(date), e);
        }
    }
    
    protected String getDateSending() {
        
        return dateFormat.format(date);
    }
}
