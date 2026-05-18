package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_info")
public class RestaurantInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Each admin has their own restaurant info
    @Column(unique = true)
    private Long userId;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String gst;
    private String fssai;
    private String website;
}