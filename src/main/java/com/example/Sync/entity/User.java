package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)  // ✅ nullable = false
    private String email;

    private String password;

    private String role; // ADMIN / STAFF

    @Column(unique = true, nullable = false)  // ✅ unique + nullable = false
    private String contactNumber;
}