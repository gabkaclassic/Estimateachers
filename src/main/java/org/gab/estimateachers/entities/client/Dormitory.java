package org.gab.estimateachers.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dormitories")
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(
            name = "title",
            nullable = false,
            unique = true
    )
    private String title;
    
    @Column(name = "total_rating")
    private double totalRating;
    
    @Column(name = "cleaning_rating")
    private double cleaningRating;
    
    @Column(name = "roommates_rating")
    private double roommatesRating;
    
    @Column(name = "capacity_rating")
    private double capacityRating;
    
    // TO DO
    @Column(name = "photos")
    @Type(type = "org.hibernate.type.BlobType")
    private Set<BufferedImage> photos = new HashSet<>();;
    
    
    @ManyToOne(
            cascade = CascadeType.ALL,
            targetEntity = University.class,
            fetch = FetchType.LAZY
    )
    private University university;
    
    @OneToMany(
            targetEntity = Student.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "dormitory"
    )
    private Set<Student> students = new HashSet<>();;
}
