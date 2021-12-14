package org.gab.estimateachers.entities.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.estimateachers.entities.client.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    
    public Request(Student student, String date, String text, RequestType requestType) {
        
        super(student, date);
        setRequestType(requestType);
        setTextRequest(text);
    }
}
