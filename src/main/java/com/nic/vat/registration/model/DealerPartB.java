package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "web_trn_dlr_part_b")
@Data
public class DealerPartB{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_number", nullable = false)
    private String applicationNumber;

    private String resStreet, resCity, resDistrict, resState, resCountry, resPincode;
    private String permStreet, permCity, permDistrict, permState, permCountry, permPincode;

   
    private String statutoryAuthority;
    private String activityCode;

    @ElementCollection
    private List<String> activityRoles;

    private String commodityName;
    private String commodityDescription;

    private LocalDate firstTaxableSaleDate;
    private String vatOption;
    private BigDecimal estimatedTurnover;
    private String filingFrequency;
}

