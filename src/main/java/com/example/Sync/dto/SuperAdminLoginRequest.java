package com.example.Sync.dto;

import lombok.Data;

@Data
public class SuperAdminLoginRequest {
    private String email;
    private String password;
}