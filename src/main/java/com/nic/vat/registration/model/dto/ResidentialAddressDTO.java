package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class ResidentialAddressDTO {
    private String addressLine;
    private String place;
    private String distCd;
    private String stCode;
    private String pin;
    private String country; // 🆕
}


