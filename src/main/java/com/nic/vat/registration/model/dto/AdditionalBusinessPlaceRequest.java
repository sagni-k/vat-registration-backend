package com.nic.vat.registration.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdditionalBusinessPlaceRequest {
    private String applicationNumber;
    private String applicantName; // not saved
    private String location; // maps to branch_loc ("W" or "O")
    private String registrationNo; // maps to reg_cst_act or reg_state_act
    private String underStateAct;  // maps to reg_state_act
    private String branchType;     // maps to branch_type
    private String name;           // maps to name
    private String street;         // maps to addr1
    private String area;           // maps to addr2
    private String city;           // maps to place
    private String district;       // maps to dist_cd (convert to code)
    private String state;          // maps to st_code
    private String pinCode;        // maps to pin
    private String telephone;      // maps to phone
    private String edrDate;        // maps to edr_amend_date
}
