package com.example.Sync.controller;

import com.example.Sync.entity.Captain;
import com.example.Sync.service.CaptainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/captains")
@RequiredArgsConstructor
// ✅ @CrossOrigin hata diya — SecurityConfig se global CORS handle ho raha hai
public class CaptainController {

    private final CaptainService service;

    @GetMapping
    public List<Captain> getAll() {
        return service.getAll();
    }

    @GetMapping("/active")
    public List<Captain> getActive() {
        return service.getActive();
    }

    @PostMapping
    public ResponseEntity<Captain> add(@RequestBody Captain captain) {
        return ResponseEntity.ok(service.add(captain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Captain> update(
            @PathVariable Long id,
            @RequestBody Captain captain) {
        return ResponseEntity.ok(service.update(id, captain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}