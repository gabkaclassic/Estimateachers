package org.gab.estimateachers.entities.system.estimations;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "faculty_estimations")
public class FacultyEstimation extends Estimation {
    
    @Column(
            name = "price_rating",
            columnDefinition = "int4 default 0",
            nullable = false
    )
    private Integer priceRating;
    
    @Column(
            name = "education_rating",
            columnDefinition = "int4 default 0",
            nullable = false
    )
    private Integer educationRating;
    
    public FacultyEstimation(Integer priceRating, Integer educationRating, User user) {
        
        setPriceRating(priceRating);
        setEducationRating(educationRating);
        setAssessor(user);
    }
    
    public Double getTotalRating() {
        
        return ((double)(priceRating + educationRating)) / 2;
    }
}
