package com.example.GDSC_insight.dto;

import lombok.Data;

@Data
public class CorporateRegisterRequest {

    private String loginId;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
}
