package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "inventory")
public class Inventory {

    // ─── Getters & Setters ──────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double stock;

    @Column(nullable = false)
    private Double minQty;

    // ─── Constructors ───────────────────────────────────────────────
    public Inventory() {}

    public Inventory(String name, String unit, Double stock, Double minQty) {
        this.name   = name;
        this.unit   = unit;
        this.stock  = stock;
        this.minQty = minQty;
    }

}