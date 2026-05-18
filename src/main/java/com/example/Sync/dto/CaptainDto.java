package com.example.Sync.dto;


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