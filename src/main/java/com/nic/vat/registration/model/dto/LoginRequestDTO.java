package com.nic.vat.registration.model.dto;

import lombok.Data;

/**
 * Login request DTO for authentication
 */
@Data
public class LoginRequest {
    private String applicationNumber;
    private String password;
    private String captcha;
}