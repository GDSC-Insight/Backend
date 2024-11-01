package com.example.GDSC_insight.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualRegisterRequest {
    private String loginId;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private String bankAccountNumber;
}