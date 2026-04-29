package com.example.Sync.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tables")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private Integer capacity;
    private String type;
    private Boolean active;
}