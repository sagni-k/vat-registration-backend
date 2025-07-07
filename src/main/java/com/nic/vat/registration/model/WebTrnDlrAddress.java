// file: WebTrnDlrAddress.java
package com.nic.vat.registration.model;

import com.nic.vat.registration.model.pk.WebTrnDlrAddressId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "web_trn_dlr_address", schema = "tvat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebTrnDlrAddress {

    @EmbeddedId
    private WebTrnDlrAddressId id;

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
    private String stateCode;

    @Column(name = "pin")
    private BigDecimal pin;

    @Column(name = "phone")
    private String phone;

    @Column(name = "edr_amend_date")
    private LocalDate edrAmendDate;

    @Column(name = "reg_state_act")
    private String regStateAct;

    @Column(name = "reg_cst_act")
    private String regCstAct;

    @Column(name = "branch_type")
    private String branchType;

    @Column(name = "branch_loc")
    private String branchLoc; // 'W' or 'O' for within/outside state
}

