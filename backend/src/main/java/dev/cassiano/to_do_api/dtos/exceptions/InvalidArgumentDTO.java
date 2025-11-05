package dev.cassiano.to_do_api.dtos.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public record InvalidArgumentDTO(
    String message,
    Map<String, String> fields
) {
    public InvalidArgumentDTO(String message, MethodArgumentNotValidException ex) {
        this(
            message, 
            ex.getFieldErrors().stream()
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage
                ))    
        );
    }
}
