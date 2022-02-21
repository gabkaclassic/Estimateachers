package org.gab.estimateachers.entities.system.applications;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "registration_applications")
public class RegistrationApplication extends Application {
    
    @Column(
            name = "card_photo",
            nullable = false
    )
    @Getter
    private String filename;
    
    public RegistrationApplication(Student student, String date, String photo) {
        
        super(student, date);
        setFilename(photo);
    }
}
