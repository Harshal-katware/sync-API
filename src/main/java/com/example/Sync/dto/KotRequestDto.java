package com.example.Sync.dto;

import lombok.Data;
import java.util.List;

/**
 * Request body for POST /api/orders/{id}/kot
 * Frontend sends only the NEW items (qty = newQty - sentQty).
 */
@Data
public class KotRequestDto {

    private List<KotItemDto> items;

    @Data
    public static class KotItemDto {
        private Long   menuId;
        private String name;
        private String emoji;
        private Double price;
        private Integer qty;   // only the new/additional qty
    }
}