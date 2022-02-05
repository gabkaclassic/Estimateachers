package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.system.estimations.DormitoryEstimation;
import org.gab.estimateachers.entities.system.estimations.Estimation;
import org.gab.estimateachers.entities.system.estimations.FacultyEstimation;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dormitories")
public class Dormitory extends Card {
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "university_dormitories",
            joinColumns = {@JoinColumn(name = "dormitory_id")},
            inverseJoinColumns = {@JoinColumn(name = "university_id")}
    )
    private University university;
    
    @OneToMany
    @JoinTable(
            name = "dormitories_estimations",
            joinColumns = @JoinColumn(name = "dormitory_id"),
            inverseJoinColumns = @JoinColumn(name = "estimation_id")
    )
    private List<DormitoryEstimation> estimations;
    
    public Double getCapacityRating() {
        
        return estimations.stream().map(DormitoryEstimation.class::cast).mapToDouble(DormitoryEstimation::getCapacityRating).average().orElse(0);
    }
    
    public Double getRoommatesRating() {
        
        return estimations.stream().map(DormitoryEstimation.class::cast).mapToDouble(DormitoryEstimation::getRoommatesRating).average().orElse(0);
    }
    
    public Double getCleaningRating() {
        
        return estimations.stream().map(DormitoryEstimation.class::cast).mapToDouble(DormitoryEstimation::getCleaningRating).average().orElse(0);
    }
    
    public Dormitory(String title, University university) {
        
        super(title);
        setUniversity(university);
        setCardType(CardType.DORMITORY);
    }
    
    public void estimation(DormitoryEstimation estimation) {
        
        estimations.add(estimation);
    }
    
    public Double getTotalRating() {
        
        return round(estimations.stream().mapToDouble(DormitoryEstimation::getTotalRating).average().orElse(0));
    }
    
    public Integer getAssessorsCount() {
        
        return estimations.size();
    }
    
    public boolean containsAssessor(User user) {
        
        return estimations.stream().map(DormitoryEstimation::getAssessor).collect(Collectors.toSet()).contains(user);
    }
}