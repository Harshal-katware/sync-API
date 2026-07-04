package com.example.Sync.controller;

import com.example.Sync.dto.PrintRequest;
import com.example.Sync.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/print")
public class PrintController {

    @Autowired
    private PrinterService printerService;

    // ── Print Bill ────────────────────────────────────────────────────────────
    @PostMapping("/bill")
    public ResponseEntity<?> printBill(@RequestBody PrintRequest order) {
        try {
            printerService.printBill(order);
            return ResponseEntity.ok("✅ Bill Printed!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Bill Print Failed: " + e.getMessage());
        }
    }

    // ── Print KOT ─────────────────────────────────────────────────────────────
    @PostMapping("/kot")
    public ResponseEntity<?> printKOT(@RequestBody PrintRequest order) {
        try {
            printerService.printKOT(order);
            return ResponseEntity.ok("✅ KOT Printed!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ KOT Print Failed: " + e.getMessage());
        }
    }
}