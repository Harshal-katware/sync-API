package com.example.Sync.exception;

// ─── Custom Exception ────────────────────────────────────────────────────────

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}