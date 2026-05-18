//package com.example.Sync.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class AuthResponse {
//
//    private String token;
//    private String name;
//    private String email;
//    private String role;
//    private String contactNumber;
//}

package com.example.Sync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String name;
    private String email;
    private String role;
    private String contactNumber;

    // ✅ Add these
    private String subscriptionStatus;
    private String subscriptionPlan;
    private LocalDate subscriptionEnd;
}