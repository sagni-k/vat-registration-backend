package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class PermanentAddressDTO {
    private String street;     // maps to: permAddr
    private String city;       // maps to: permPlace
    private String district;   // maps to: permDistCd
    private String state;      // maps to: permStCode
    private String country;    // maps to: permCountry
    private String pinCode;    // maps to: permPin
}
