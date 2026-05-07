package com.example.Sync.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {

    private Long   tableId;
    private String tableName;

    private List<OrderItemDto> items;

    private Double subtotal;
    private Double discount;
    private Double gst;
    private Double serviceCharge;
    private Double billCharge;
    private Double total;

    @Data
    public static class OrderItemDto {
        private Long   menuId;
        private String name;
        private String emoji;
        private Double price;
        private Integer qty;
    }
}