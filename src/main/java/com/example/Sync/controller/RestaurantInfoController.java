package com.example.Sync.controller;

import com.example.Sync.entity.RestaurantInfo;
import com.example.Sync.service.RestaurantInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "http://localhost:5173")
public class RestaurantInfoController {

    private final RestaurantInfoService service;

    public RestaurantInfoController(RestaurantInfoService service) {
        this.service = service;
    }

    @GetMapping
    public RestaurantInfo getInfo() {
        return service.getInfo();
    }

    @PutMapping
    public RestaurantInfo saveInfo(@RequestBody RestaurantInfo info) {
        return service.saveInfo(info);
    }
}