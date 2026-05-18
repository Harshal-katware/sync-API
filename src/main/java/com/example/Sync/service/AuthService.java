//package com.example.Sync.service;
//
//import com.example.Sync.dto.*;
//import com.example.Sync.entity.User;
//import com.example.Sync.repository.UserRepository;
//import com.example.Sync.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository repo;
//    private final JwtUtil jwtUtil;
//    private final BCryptPasswordEncoder encoder;
//
//    public String register(RegisterRequest request) {
//
//        if (repo.findByEmail(request.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        if (repo.findByContactNumber(request.getContactNumber()).isPresent()) {
//            throw new RuntimeException("Contact number already registered");
//        }
//
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(encoder.encode(request.getPassword()));
//        user.setRole("ADMIN");
//        user.setContactNumber(request.getContactNumber());
//
//        repo.save(user);
//        return "Registered Successfully";
//    }

//    public AuthResponse login(LoginRequest request) {
//
//        // Email or contact number login
//        User user = repo.findByEmailOrContact(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!encoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        String token = jwtUtil.generateToken(user.getEmail());
//
//        return new AuthResponse(
//                token,
//                user.getName(),
//                user.getEmail(),
//                user.getRole(),
//                user.getContactNumber()
//        );
//    }

//    public AuthResponse login(LoginRequest request) {
//        User user = repo.findByEmailOrContact(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!encoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        // ✅ Check if trial/plan expired and update status
//        if (user.getSubscriptionEnd() != null &&
//                user.getSubscriptionEnd().isBefore(LocalDate.now()) &&
//                user.getSubscriptionStatus() != User.SubscriptionStatus.EXPIRED) {
//
//            user.setSubscriptionStatus(User.SubscriptionStatus.EXPIRED);
//            repo.save(user);
//        }
//
//        String token = jwtUtil.generateToken(user.getEmail());
//
//        return new AuthResponse(
//                token,
//                user.getName(),
//                user.getEmail(),
//                user.getRole(),
//                user.getContactNumber(),
//                user.getSubscriptionStatus().name(),
//                user.getSubscriptionPlan().name(),
//                user.getSubscriptionEnd()
//        );
//    }
//
//    public String changePassword(String authHeader, ChangePasswordRequest request) {
//        String token = authHeader.substring(7);
//        String email = jwtUtil.getEmailFromToken(token);
//
//        User user = repo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            throw new RuntimeException("Current password is wrong");
//        }
//
//        user.setPassword(encoder.encode(request.getNewPassword()));
//        repo.save(user);
//        return "Password changed successfully";
//    }
//
//    public String register(RegisterRequest request) {
//        if (repo.findByEmail(request.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//        if (repo.findByContactNumber(request.getContactNumber()).isPresent()) {
//            throw new RuntimeException("Contact number already registered");
//        }
//
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(encoder.encode(request.getPassword()));
//        user.setRole("ADMIN");
//        user.setContactNumber(request.getContactNumber());
//
//        // ✅ Auto start 7 day trial
//        user.setSubscriptionStatus(User.SubscriptionStatus.TRIAL);
//        user.setSubscriptionPlan(User.SubscriptionPlan.TRIAL);
//        user.setSubscriptionStart(LocalDate.now());
//        user.setSubscriptionEnd(LocalDate.now().plusDays(7));
//
//        repo.save(user);
//        return "Registered Successfully";
//    }
//}



package com.example.Sync.service;

import com.example.Sync.dto.*;
import com.example.Sync.entity.User;
import com.example.Sync.repository.UserRepository;
import com.example.Sync.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public String register(RegisterRequest request) {
        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (repo.findByContactNumber(request.getContactNumber()).isPresent()) {
            throw new RuntimeException("Contact number already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        user.setContactNumber(request.getContactNumber());

        // ✅ Auto 7 day trial
        user.setSubscriptionStatus(User.SubscriptionStatus.TRIAL);
        user.setSubscriptionPlan(User.SubscriptionPlan.TRIAL);
        user.setSubscriptionStart(LocalDate.now());
        user.setSubscriptionEnd(LocalDate.now().plusDays(7));

        repo.save(user);
        return "Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + request.getEmail());

        User user = repo.findByEmailOrContact(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User found: " + user.getEmail());
        System.out.println("Subscription Status: " + user.getSubscriptionStatus());
        System.out.println("Subscription End: " + user.getSubscriptionEnd());

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getContactNumber(),
                user.getSubscriptionStatus().name(),
                user.getSubscriptionPlan().name(),
                user.getSubscriptionEnd()
        );
    }

    public String changePassword(String authHeader, ChangePasswordRequest request) {
        String token = authHeader.substring(7);
        String email = jwtUtil.getEmailFromToken(token);
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is wrong");
        }
        user.setPassword(encoder.encode(request.getNewPassword()));
        repo.save(user);
        return "Password changed successfully";
    }
}