package com.example.Sync.config;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        if (message.contains("already")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message); // 409
        }
        if (message.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message); // 404
        }
        if (message.contains("password") || message.contains("Invalid")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message); // 401
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message); // 400
    }
}