package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity(name = "requests")
@NoArgsConstructor
public class Request extends Application {
    
    @Column(
            name = "request_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    
    @Column(name = "text")
    private String textRequest;
    
    @ElementCollection(
            targetClass = String.class,
            fetch = FetchType.LAZY
    )
    private Set<String> photos;
    
    public Request(Student student, String date, String text, RequestType requestType) {
        
        super(student, date);
        setRequestType(requestType);
        setTextRequest(text);
        photos = new HashSet<>();
    }
    
    public void addPhoto(String filename) {
        
        if(Objects.nonNull(filename) && !filename.isEmpty())
            photos.add(filename);
    }
}
