package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String applicationNumber;
    private String dateOfBirth;
    private String captcha;
}

