package org.gab.estimateachers.entities.system.estimations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher_estimations")
public class TeacherEstimation extends Estimation {
    
    @Column(
            name = "severity_rating",
            columnDefinition = "int4 default 0"
    )
    @Getter
    @Setter
    private Integer severityRating;
    
    @Column(
            name = "exacting_rating",
            columnDefinition = "int4 default 0"
    )
    @Getter
    @Setter
    private Integer exactingRating;
    
    @Column(
            name = "freebies_rating",
            columnDefinition = "int4 default 0"
    )
    @Getter
    @Setter
    private Integer freebiesRating;
    
    public TeacherEstimation(Integer severityRating, Integer exactingRating, Integer freebiesRating, User user) {
    
        setSeverityRating(severityRating);
        setExactingRating(exactingRating);
        setFreebiesRating(freebiesRating);
        setAssessor(user);
    }
    
    public Double getTotalRating() {
        
        return  ((double)(severityRating + exactingRating + freebiesRating)) / 3;
    }
}