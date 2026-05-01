package com.example.Sync.Exception;

// ─── Custom Exception ────────────────────────────────────────────────────────

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}