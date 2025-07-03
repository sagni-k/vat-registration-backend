package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "\"web_trn_dlr_mst\"", schema = "tvat")

@Data
public class DealerMaster {

    @Id
    @Column(name = "ack_no", nullable = false)
    private BigDecimal ackNo;

    @Column(name = "\"ack_date\"", nullable = false)
    private LocalDate ackDate;


    @Column(name = "tin_no")
    private BigDecimal tinNo;

    @Column(name = "dc_no")
    private BigDecimal dcNo;

    @Column(name = "appl_name_s")
    private String applNameS;

    @Column(name = "appl_name_g")
    private String applNameG;

    @Column(name = "trad_name")
    private String tradName;

    @Column(name = "bus_addr1")
    private String busAddr1;

    @Column(name = "bus_addr2")
    private String busAddr2;

    @Column(name = "bus_plac")
    private String busPlac;

    @Column(name = "bus_dist_cd")
    private BigDecimal busDistCd;

    @Column(name = "bus_pin_cd")
    private BigDecimal busPinCd;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "bus_status")
    private BigDecimal busStatus;

    @Column(name = "fath_name")
    private String fathName;

    @Column(name = "pan")
    private String pan;

    @Column(name = "dt_birth")
    private LocalDate dtBirth;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "office")
    private String office;

    @Column(name = "reg_type")
    private String regType;

    @Column(name = "mobile")
    private BigDecimal mobile;

    @Column(name = "resi_add1")
    private String resiAdd1;

    @Column(name = "resi_addr2")
    private String resiAddr2;

    @Column(name = "resi_place")
    private String resiPlace;

    @Column(name = "resi_dist_cd")
    private BigDecimal resiDistCd;

    @Column(name = "resi_pin")
    private BigDecimal resiPin;

    @Column(name = "perm_addr")
    private String permAddr;

    @Column(name = "perm_place")
    private String permPlace;

    @Column(name = "perm_pin")
    private BigDecimal permPin;

    @Column(name = "perm_dist_cd")
    private BigDecimal permDistCd;

    @Column(name = "reg_central_excise")
    private String regCentralExcise;

    @Column(name = "certf_iss_muni")
    private String tradeLicenseNo;

    @Column(name = "dt_iss_certf_munci")
    private LocalDate tradeLicenseIssueDate;

    @Column(name = "dt_rnew_certf_munci")
    private LocalDate tradeLicenseRenewalDate;

    @Column(name = "acc_year_from")
    private String accYearFrom;

    @Column(name = "acc_year_to")
    private String accYearTo;

    @Column(name = "sal_last_quat")
    private BigDecimal saleLastQuarter;

    @Column(name = "sal_last_year")
    private BigDecimal saleLastYear;

    @Column(name = "shpestact_licno")
    private String shopLicenseNo;

    @Column(name = "shpestact_licno_issdt")
    private LocalDate shopLicenseIssueDate;

    @Column(name = "foodstaff_licno")
    private String foodLicenseNo;

    @Column(name = "foodstaff_licno_issdt")
    private LocalDate foodLicenseIssueDate;

    @Column(name = "isindian")
    private String isIndian; // Use "Y"/"N" or "1"/"0"

    @Column(name = "perm_st_code")
    private String permStCode;

    @Column(name = "resi_st_code")
    private String resiStCode;

    @Column(name = "activity_code")
    private String activityCode;

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "commodity_description")
    private String commodityDescription;

    @Column(name = "first_taxable_sale_date")
    private LocalDate firstTaxableSaleDate;

    @Column(name = "vat_option")
    private String vatOption;

    @Column(name = "estimated_turnover")
    private BigDecimal estimatedTurnover;

    @Column(name = "filing_frequency")
    private String filingFrequency;


// ... You can continue adding other fields similarly based on project need.
}




