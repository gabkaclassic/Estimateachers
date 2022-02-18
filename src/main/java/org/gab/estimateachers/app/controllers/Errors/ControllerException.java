package org.gab.estimateachers.app.controllers.Errors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ControllerException extends RuntimeException {
    
    private final String ERROR_MESSAGE = """
            Error occurred: %s\n
            Reason: %s\n
            """;
    
    private static final String REASON_ERROR = "Server error or invalid link";
    
    @Getter
    @Setter
    private final String message;
    @Getter
    @Setter
    private final HttpStatus httpStatus;
    @Getter
    @Setter
    private final ZonedDateTime zonedDateTime;
    
    public ControllerException(String message, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        
        this.message = String.format(ERROR_MESSAGE, message, REASON_ERROR);;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
