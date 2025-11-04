package dev.cassiano.to_do_api.exceptions;

import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(Exception ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> genericException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
