package com.example.Sync.entity;

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
}