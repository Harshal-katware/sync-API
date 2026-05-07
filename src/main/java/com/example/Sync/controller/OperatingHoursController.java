package com.example.Sync.controller;

import com.example.Sync.entity.OperatingHours;
import com.example.Sync.service.OperatingHoursService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hours")
@CrossOrigin(origins = "http://localhost:5173")
public class OperatingHoursController {

    private final OperatingHoursService service;

    public OperatingHoursController(OperatingHoursService service) {
        this.service = service;
    }

    @GetMapping
    public List<OperatingHours> getAllHours() {
        return service.getAllHours();
    }

    @PutMapping("/{id}")
    public OperatingHours updateHours(@PathVariable Long id, @RequestBody OperatingHours hours) {
        return service.updateHours(id, hours);
    }

    @PostMapping("/save-all")
    public List<OperatingHours> saveAll(@RequestBody List<OperatingHours> hours) {
        return service.saveAll(hours);
    }
}