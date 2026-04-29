package com.example.Sync.service;

import com.example.Sync.entity.Inventory;
import com.example.Sync.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    // ── Get all items ────────────────────────────────────────────────
    public List<Inventory> getAll() {
        return repo.findAll();
    }

    // ── Create a new item ────────────────────────────────────────────
    public Inventory create(Inventory item) {
        if (item.getName() == null || item.getName().isBlank())
            throw new IllegalArgumentException("Item name is required.");
        if (item.getUnit() == null || item.getUnit().isBlank())
            throw new IllegalArgumentException("Unit is required.");
        if (item.getMinQty() == null || item.getMinQty() < 0)
            throw new IllegalArgumentException("Min quantity must be 0 or greater.");
        if (item.getStock() == null)
            item.setStock(0.0);
        if (repo.existsByNameIgnoreCase(item.getName().trim()))
            throw new IllegalArgumentException("An item with this name already exists.");

        item.setName(item.getName().trim());
        return repo.save(item);
    }

    // ── Stock In ─────────────────────────────────────────────────────
    public Inventory stockIn(Long id, Double qty) {
        if (qty == null || qty <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero.");

        Inventory item = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        item.setStock(item.getStock() + qty);
        return repo.save(item);
    }

    // ── Mark Used (Stock Out) ────────────────────────────────────────
    public Inventory markUsed(Long id, Double qty) {
        if (qty == null || qty <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero.");

        Inventory item = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        if (qty > item.getStock())
            throw new IllegalArgumentException(
                    "Insufficient stock. Only " + item.getStock() + " " + item.getUnit() + " available.");

        item.setStock(item.getStock() - qty);
        return repo.save(item);
    }
}