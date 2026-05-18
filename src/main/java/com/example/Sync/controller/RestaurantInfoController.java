package com.example.Sync.controller;

import com.example.Sync.entity.RestaurantInfo;
import com.example.Sync.service.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RestaurantInfoController {

    private final RestaurantInfoService service;

    // ✅ Pass Authorization header to get THIS admin's info
    @GetMapping
    public RestaurantInfo getInfo(
            @RequestHeader("Authorization") String authHeader
    ) {
        return service.getInfo(authHeader);
    }

    // ✅ Pass Authorization header to save THIS admin's info
    @PutMapping
    public RestaurantInfo saveInfo(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RestaurantInfo info
    ) {
        return service.saveInfo(authHeader, info);
    }
}