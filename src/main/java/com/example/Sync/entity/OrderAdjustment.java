package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Data
@Entity
public class OrderAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String reason;

    private String type;
    // REFUND
    // WRONG_ENTRY
    // VOID_ITEM
    // TAX_CORRECTION

    private String adjustedBy;

    private LocalDateTime adjustedAt;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}