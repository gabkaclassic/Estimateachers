package org.gab.estimateachers.app.controllers.Errors;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    
    @Recover
    @ExceptionHandler(value = ControllerException.class)
    public ResponseEntity<Object> error(ControllerException exception) {
        
        log.warn(String.format("Exception: %s, reason: %s", exception.getMessage(), exception.getCause()));
        Sentry.captureException(exception);
        ControllerException e = new ControllerException(exception.getMessage(), exception.getHttpStatus(),
                exception.getZonedDateTime());
    
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
