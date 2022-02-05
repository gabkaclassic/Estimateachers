package org.gab.estimateachers.entities.system.estimations;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dormitory_estimations")
public class DormitoryEstimation extends Estimation {
    
    @Column(
            name = "cleaning_rating",
            columnDefinition = "int4 default 0"
    )
    private Integer cleaningRating;
    
    @Column(
            name = "roommates_rating",
            columnDefinition = "int4 default 0"
    )
    private Integer roommatesRating;
    
    @Column(
            name = "capacity_rating",
            columnDefinition = "int4 default 0"
    )
    private Integer capacityRating;
    
    public DormitoryEstimation(Integer cleaningRating, Integer roommatesRating, Integer capacityRating, User user) {
        
        setCapacityRating(capacityRating);
        setCleaningRating(cleaningRating);
        setRoommatesRating(roommatesRating);
        setAssessor(user);
    }
    
    public Double getTotalRating() {
        
        return ((double)(cleaningRating + roommatesRating + capacityRating)) / 3;
    }
}
