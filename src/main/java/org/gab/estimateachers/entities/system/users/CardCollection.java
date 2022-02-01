package org.gab.estimateachers.entities.system.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.CardType;

import javax.persistence.*;

@Entity
@Table(name = "card_collection", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_id", "card_type"}))
@Data
@NoArgsConstructor
public class CardCollection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;
    
    @Column(
            name = "card_id",
            nullable = false
    )
    private Long cardId;
    
    @Column(
            name = "card_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    
    public CardCollection(Long userId, Long cardId, CardType cardType) {
        
        setUserId(userId);
        setCardId(cardId);
        setCardType(cardType);
    }
    
    public CardCollection(Long userId, Long cardId, String cardType) {
        
        setUserId(userId);
        setCardId(cardId);
        setCardType(CardType.valueOf(cardType));
    }
}

