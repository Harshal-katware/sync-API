package com.example.Sync.service;

import com.example.Sync.dto.*;
import com.example.Sync.entity.SuperAdmin;
import com.example.Sync.repository.SuperAdminRepository;
import com.example.Sync.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final SuperAdminRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    // ✅ Login
    public SuperAdminResponse login(SuperAdminLoginRequest request) {
        SuperAdmin admin = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Super Admin not found"));

        if (!encoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        return new SuperAdminResponse(
                token,
                admin.getName(),
                admin.getEmail(),
                "SUPER_ADMIN",
                admin.getContactNumber()
        );
    }

    // ✅ Create Super Admin (only from backend)
    public String createSuperAdmin(SuperAdminRegisterRequest request) {
        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        SuperAdmin admin = new SuperAdmin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(encoder.encode(request.getPassword()));
        admin.setContactNumber(request.getContactNumber());

        repo.save(admin);
        return "Super Admin created successfully!";
    }

    // ✅ Get all super admins
    public java.util.List<SuperAdmin> getAllSuperAdmins() {
        return repo.findAll();
    }
}