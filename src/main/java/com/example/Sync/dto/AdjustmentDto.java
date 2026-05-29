package com.example.Sync.dto;

import lombok.Data;

@Data
public class AdjustmentDto {

    private Double amount;

    private String reason;

    private String type;

    private String adjustedBy;
}