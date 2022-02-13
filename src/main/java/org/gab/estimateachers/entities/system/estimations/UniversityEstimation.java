package org.gab.estimateachers.entities.system.estimations;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "university_estimations")
@NoArgsConstructor
@Data
public class UniversityEstimation extends Estimation{
    
    @Column(
            name = "price_rating",
            columnDefinition = "int4 default 0",
            nullable = false
    )
    private Integer priceRating;
    
    @Column(
            name = "complexity_rating",
            columnDefinition = "int4 default 0",
            nullable = false
    )
    private Integer complexityRating;
    
    @Column(
            name = "utility_rating",
            columnDefinition = "int4 default 0",
            nullable = false
    )
    private Integer utilityRating;
    
    public UniversityEstimation(Integer priceRating, Integer complexityRating, Integer utilityRating, User user) {
        
        setPriceRating(priceRating);
        setComplexityRating(complexityRating);
        setUtilityRating(utilityRating);
        setAssessor(user);
    }
    
    public Double getTotalRating() {
        
        return ((double)(complexityRating + utilityRating + priceRating)) / 3;
    }
}