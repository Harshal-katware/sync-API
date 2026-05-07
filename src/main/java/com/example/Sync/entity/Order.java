package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long   tableId;
    private String tableName;

    // "OPEN" | "SAVED" | "SETTLED"
    private String status;

    // "CASH" | "CARD" | "UPI" | "ONLINE"
    private String paymentMode;

    private Double subtotal;
    private Double discount;
    private Double gst;
    private Double serviceCharge;
    private Double billCharge;
    private Double total;

    private LocalDateTime createdAt;
    private LocalDateTime settledAt;

    // ✅ mappedBy = "order" — OrderItem.order field se link
    // ✅ @JoinColumn hataya — mappedBy ke saath @JoinColumn nahi chahiye Order side pe
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}