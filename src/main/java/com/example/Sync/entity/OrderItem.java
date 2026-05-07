package com.example.Sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long    menuId;
    private String  name;
    private String  emoji;
    private Double  price;
    private Integer qty;

    // ✅ Order ke saath ManyToOne relation — order_id column DB mein save hoga
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore   // JSON mein circular reference avoid karne ke liye
    private Order order;
}