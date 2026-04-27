package com.example.Sync.service;

import com.example.Sync.dto.*;
import com.example.Sync.entity.User;
import com.example.Sync.repository.UserRepository;
import com.example.Sync.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder; // ✅ @Bean se inject hoga

    public String register(RegisterRequest request) {

        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ADMIN");

        repo.save(user);

        return "Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public String changePassword(String authHeader, ChangePasswordRequest request) {

        // ✅ Token se email nikalo
        String token = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(token);

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Current password verify karo
        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is wrong");
        }

        // ✅ Naya password encode karke save karo
        user.setPassword(encoder.encode(request.getNewPassword()));
        repo.save(user);

        return "Password changed successfully";
    }
}