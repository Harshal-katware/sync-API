package com.example.Sync.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuantityDTO {

    private Double qty;

    public QuantityDTO() {}

    public QuantityDTO(Double qty) {
        this.qty = qty;
    }

}