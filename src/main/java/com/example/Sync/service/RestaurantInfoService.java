package com.example.Sync.service;

import com.example.Sync.entity.RestaurantInfo;
import com.example.Sync.entity.User;
import com.example.Sync.repository.RestaurantInfoRepository;
import com.example.Sync.repository.UserRepository;
import com.example.Sync.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantInfoService {

    private final RestaurantInfoRepository repository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // ✅ Extract userId from JWT token
    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    // ✅ Get THIS admin's restaurant info only
    public RestaurantInfo getInfo(String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return repository.findByUserId(userId)
                .orElseGet(() -> {
                    // Return empty info with userId set — default blank
                    RestaurantInfo empty = new RestaurantInfo();
                    empty.setUserId(userId);
                    return empty;
                });
    }

    // ✅ Save/update THIS admin's restaurant info only
    public RestaurantInfo saveInfo(String authHeader, RestaurantInfo info) {
        Long userId = getUserIdFromToken(authHeader);

        return repository.findByUserId(userId)
                .map(existing -> {
                    // Update existing record
                    existing.setName(info.getName());
                    existing.setEmail(info.getEmail());
                    existing.setPhone(info.getPhone());
                    existing.setAddress(info.getAddress());
                    existing.setGst(info.getGst());
                    existing.setFssai(info.getFssai());
                    existing.setWebsite(info.getWebsite());
                    return repository.save(existing);
                })
                .orElseGet(() -> {
                    // Create new record for this admin
                    info.setUserId(userId);
                    return repository.save(info);
                });
    }
}