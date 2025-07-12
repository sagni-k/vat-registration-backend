package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class ResidentialAddressDTO {
    private String street;     // maps to: resiAdd1
    private String city;       // maps to: resiPlace
    private String district;   // maps to: resiDistCd
    private String state;      // maps to: resiStCode
    private String country;    // maps to: resiCountry
    private String pinCode;    // maps to: resiPin
}
