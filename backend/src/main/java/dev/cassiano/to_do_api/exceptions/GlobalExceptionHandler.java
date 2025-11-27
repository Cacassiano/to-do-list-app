package dev.cassiano.to_do_api.exceptions;

import dev.cassiano.to_do_api.dtos.exceptions.InvalidArgumentDTO;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> badRequestHandler(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid request -> "+ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidArgumentDTO> invalidRequest(MethodArgumentNotValidException ex) {
        InvalidArgumentDTO res = new InvalidArgumentDTO("The given values are violating the field constraints",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @SuppressWarnings("null")
    public ResponseEntity<String> invalidType(MethodArgumentTypeMismatchException ex) {
        String message = "The property '"+ex.getPropertyName()+"' must be of type '"+ex.getRequiredType().getSimpleName()+"'";
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataViolation(DataIntegrityViolationException ex) {
        String message = ex.getMessage().split("ERROR: ")[1].split("Detalhe:")[0].split('"'+"")[0];
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
