package org.gab.estimateachers.app.controllers.Errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ControllerException extends RuntimeException {
    
    private static final String ERROR_MESSAGE = """
            Error occurred: %s\n
            Reason: %s\n
            """;
    
    private static final String REASON_ERROR = "Server error or invalid link";
    
    @Getter
    private final String message;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final ZonedDateTime zonedDateTime;
    
    public ControllerException(String message, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        
        this.message = String.format(ERROR_MESSAGE, message, REASON_ERROR);;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
