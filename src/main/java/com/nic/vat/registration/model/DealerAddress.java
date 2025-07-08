package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "web_trn_dlr_address", schema = "tvat")
@IdClass(DealerAddressId.class)
@Data
public class DealerAddress {

    @Id
    @Column(name = "tin_no")
    private BigDecimal tinNo;

    @Id
    @Column(name = "ack_no")
    private BigDecimal ackNo;

    @Id
    @Column(name = "sno")
    private BigDecimal sno;

    @Column(name = "name")
    private String name;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "place")
    private String place;

    @Column(name = "dist_cd")
    private BigDecimal distCd;

    @Column(name = "st_code")
    private String stCode;

    @Column(name = "pin")
    private BigDecimal pin;

    @Column(name = "phone")
    private String phone;

    @Column(name = "branch_type")
    private String branchType;

    @Column(name = "branch_loc")
    private String branchLoc;

    @Column(name = "reg_cst_act")
    private String regCstAct;

    @Column(name = "reg_state_act")
    private String regStateAct;

    @Column(name = "edr_amend_date")
    private LocalDate edrAmendDate;

}

