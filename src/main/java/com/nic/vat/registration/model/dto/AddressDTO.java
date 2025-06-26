package com.nic.vat.registration.model.dto;
import lombok.Data;
@Data
public class AddressDTO {

    private String roomNo;
    private String area;
    private String village;
    private String district;
    private String pinCode;
    private String occupancyStatus;

    // Getters and setters
}
