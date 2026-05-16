package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "captains")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Captain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false)
    private Boolean active = true;
}