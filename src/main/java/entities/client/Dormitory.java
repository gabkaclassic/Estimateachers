package entities.client;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.Set;

@Entity
@Table(name = "dormitories")
@Data
@ToString
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "title")
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
    private Set<BufferedImage> photos;
    
    
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
    private Set<Student> students;
}
