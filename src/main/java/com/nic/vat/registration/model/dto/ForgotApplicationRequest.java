package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class ForgotApplicationRequest {
    private String applicantName;
    private String fathersName;
    private String dateOfBirth; // in YYYY-MM-DD format
}

