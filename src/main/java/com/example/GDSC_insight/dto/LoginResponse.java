package com.example.GDSC_insight.dto;


import lombok.Data;

@Data
public class LoginResponse {

    private final boolean success;
    private final String loginId;
}
