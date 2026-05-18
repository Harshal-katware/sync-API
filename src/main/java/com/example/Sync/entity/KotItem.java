package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Represents one line on a KOT (Kitchen Order Ticket).
 * Every time the waiter clicks "Print KOT", new KotItem rows are inserted
 * for ONLY the newly-added quantities — not the full order again.
 */
@Entity
@Table(name = "kot_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KotItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The parent order this KOT belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** Which KOT round this is (1st print = 1, 2nd print = 2, ...) */
    @Column(nullable = false)
    private Integer kotRound;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private String name;

    private String emoji;

    @Column(nullable = false)
    private Double price;

    /** Quantity printed on THIS specific KOT (not cumulative) */
    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private LocalDateTime printedAt;
}