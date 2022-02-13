package org.gab.estimateachers.entities.system.estimations;

import lombok.Data;
import org.gab.estimateachers.entities.system.users.User;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Estimation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    
    @OneToOne
    @JoinColumn(name = "assessor_id")
    protected User assessor;
    
    public abstract Double getTotalRating();
}
