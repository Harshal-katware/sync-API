package com.example.Sync.controller;

import com.example.Sync.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // ── Today's Report ──────────────────────────────────────────────
    @GetMapping("/daily")
    public ResponseEntity<Map<String, Object>> getDailyReport() {
        return ResponseEntity.ok(reportService.getDailyReport(LocalDate.now()));
    }

    // ── Monthly Report ──────────────────────────────────────────────
    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyReport(
            @RequestParam(defaultValue = "0") int month, // 0 = current month
            @RequestParam(defaultValue = "0") int year   // 0 = current year
    ) {
        LocalDate now = LocalDate.now();
        int m = month == 0 ? now.getMonthValue() : month;
        int y = year  == 0 ? now.getYear()       : year;
        return ResponseEntity.ok(reportService.getMonthlyReport(m, y));
    }

    // ── Top Selling Products ────────────────────────────────────────
    @GetMapping("/top-products")
    public ResponseEntity<Map<String, Object>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(reportService.getTopProducts(limit));
    }

    // ── Custom Report (filter by payment + orderType + date range) ──
    @GetMapping("/custom")
    public ResponseEntity<Map<String, Object>> getCustomReport(
            @RequestParam(required = false) String payment,
            @RequestParam(required = false) String orderType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(reportService.getCustomReport(payment, orderType, from, to));
    }
}