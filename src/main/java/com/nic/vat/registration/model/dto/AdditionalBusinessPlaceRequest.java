package com.nic.vat.registration.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdditionalBusinessPlaceRequest {
    private String applicationNumber;
    private String location;
    private String registrationNo;
    private String underStateAct;
    private String branchType;
    private String name;
    private String street;
    private String area;
    private String city;
    private String district;
    private String state;
    private String pinCode;
    private String telephone;
    private LocalDate edrDate;
}
