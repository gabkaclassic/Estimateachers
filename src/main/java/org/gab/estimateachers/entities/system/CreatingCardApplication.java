package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "creating_card_applications")
public class CreatingCardApplication extends Application {
    
    @Column(
            name = "card_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    
    @Column(name = "card_title")
    private String cardTitle;
    
    public CreatingCardApplication(Student student, String date, CardType type, String title) {
        
        super(student, date);
        setCardType(type);
        setCardTitle(title);
    }

}
