package com.example.Sync.controller;

import com.example.Sync.entity.User;
import com.example.Sync.repository.UserRepository;
import com.example.Sync.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminSubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return subscriptionService.getAllUsers();
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<?> activate(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String plan = body.get("plan");
        User updated = subscriptionService.activateSubscription(id, plan);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        User updated = subscriptionService.deactivateSubscription(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {

        user.setPassword(
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                        .encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }
}
//
//@RestController
//@RequestMapping("/api/super-admin")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
//public class AdminSubscriptionController {
//
//    private final SubscriptionService subscriptionService;
//    private final UserRepository userRepository;
//
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return subscriptionService.getAllUsers();
//    }
//
//    @PutMapping("/users/{id}/activate")
//    public ResponseEntity<?> activate(
//            @PathVariable Long id,
//            @RequestBody Map<String, String> body) {
//        String plan = body.get("plan");
//        User updated = subscriptionService.activateSubscription(id, plan);
//        return ResponseEntity.ok(updated);
//    }
//
//    @PutMapping("/users/{id}/deactivate")
//    public ResponseEntity<?> deactivate(@PathVariable Long id) {
//        User updated = subscriptionService.deactivateSubscription(id);
//        return ResponseEntity.ok(updated);
//    }
//}

