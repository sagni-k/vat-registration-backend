package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class PartARequest {

    private String applicationNumber; // ✅ Added for PUT API
    private String typeOfRegistration;
    private String office;
    private String businessConstitution;
    private String applicantName;
    private String fathersName;
    private String dateOfBirth;
    private String gender;
    private String tradingName;
    private String pan;
    private AddressDTO address;
    private ContactDTO contact;
}
