package com.example.Sync.Controller;

import com.example.Sync.dto.QuantityDTO;
import com.example.Sync.entity.Inventory;
import com.example.Sync.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // ── GET /api/inventory ───────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ── POST /api/inventory ──────────────────────────────────────────
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Inventory item) {
        try {
            Inventory created = service.create(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ── PATCH /api/inventory/{id}/stock-in ───────────────────────────
    @PatchMapping("/{id}/stock-in")
    public ResponseEntity<?> stockIn(
            @PathVariable Long id,
            @RequestBody QuantityDTO dto) {
        try {
            Inventory updated = service.stockIn(id, dto.getQty());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ── PATCH /api/inventory/{id}/mark-used ──────────────────────────
    @PatchMapping("/{id}/mark-used")
    public ResponseEntity<?> markUsed(
            @PathVariable Long id,
            @RequestBody QuantityDTO dto) {
        try {
            Inventory updated = service.markUsed(id, dto.getQty());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}