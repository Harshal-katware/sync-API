
package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role;

    @Column(unique = true, nullable = false)
    private String contactNumber;

    private String resetToken;

    private LocalDateTime resetTokenExpiry;

    // ✅ Subscription fields
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private LocalDate subscriptionStart;
    private LocalDate subscriptionEnd;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;

    // ✅ Enums inside class
    public enum SubscriptionStatus {
        TRIAL, ACTIVE, EXPIRED
    }

    public enum SubscriptionPlan {
        TRIAL,
        BASIC,
        STANDARD,
        PREMIUM
    }
}