package com.example.Sync.controller;

import com.example.Sync.entity.Tax;
import com.example.Sync.service.TaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/taxes")
@CrossOrigin(origins = "http://localhost:5173")
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public List<Tax> getAllTaxes() {
        return taxService.getAllTaxes();
    }

    @PostMapping
    public Tax addTax(@RequestBody Tax tax) {
        return taxService.addTax(tax);
    }

    @PutMapping("/{id}")
    public Tax updateTax(@PathVariable Long id, @RequestBody Tax tax) {
        return taxService.updateTax(id, tax);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}