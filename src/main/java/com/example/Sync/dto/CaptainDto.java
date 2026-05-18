package com.example.Sync.dto;// src/main/java/com/yourapp/dto/CaptainDto.java


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaptainDto {
    private Long    id;
    private String  name;
    private String  phone;
    private boolean active;
}