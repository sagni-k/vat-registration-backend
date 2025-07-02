package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String applicationNumber;
    private String password;
    private String captcha;
}

