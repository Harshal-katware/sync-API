package com.example.Sync.controller;

import com.example.Sync.dto.*;
import com.example.Sync.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/super-admin/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminAuthController {

    private final SuperAdminService superAdminService;

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SuperAdminLoginRequest request) {
        try {
            return ResponseEntity.ok(superAdminService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // ✅ Create Super Admin
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SuperAdminRegisterRequest request) {
        try {
            return ResponseEntity.ok(superAdminService.createSuperAdmin(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}