package org.gab.estimateachers.entities.system.applications;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;
import org.gab.estimateachers.entities.client.CardType;

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
    
    @Column(
            name = "card_id",
            nullable = false
    )
    private Long cardId;
    
    public CreatingCardApplication(Student student, String date, CardType type, Long cardId) {
        
        super(student, date);
        setCardType(type);
        setCardId(cardId);
    }

}
