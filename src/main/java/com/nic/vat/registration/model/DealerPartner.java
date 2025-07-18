package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "web_trn_dlr_prtnr", schema = "tvat")
@IdClass(DealerPartnerId.class)
@Data
public class DealerPartner {

    @Id
    @Column(name = "ack_no")
    private BigDecimal ackNo;

    @Id
    @Column(name = "sno")
    private BigDecimal sno;

    @Column(name = "tin_no")
    private BigDecimal tinNo;

    @Column(name = "dc_no")
    private BigDecimal dcNo;

    @Column(name = "partner_type")
    private String partnerType;

    @Column(name = "p_name")
    private String name;

    @Column(name = "fath_name")
    private String fathersName;

    @Column(name = "p_dt_birth")
    private LocalDate dateOfBirth;

    @Column(name = "p_designation")
    private String designation;

    @Column(name = "p_quali")
    private String qualification;

    @Column(name = "pan")
    private String pan;

    @Column(name = "p_addr1")
    private String addressLine1;

    @Column(name = "p_area")
    private String area;

    @Column(name = "p_place")
    private String village;

    @Column(name = "perm_addr1")
    private String permanentAddress1;

    @Column(name = "p_telephone")
    private String telephone;

    @Column(name = "p_faxno")
    private String fax;

    @Column(name = "p_email")
    private String email;

    @Column(name = "extent_of_interest")
    private BigDecimal interestPercent;

    @Column(name = "p_voteridno")
    private String voterId;

    @Column(name = "p_resi_certificateno")
    private String residentialCertNo;

    @Column(name = "p_dt_ent_pship")
    private LocalDate entryDate;

    @Column(name = "p_dt_lv_pship")
    private LocalDate exitDate;



    @Column(name = "doc_id_name")
    private String docIdName;

    @Column(name = "doc_id_type")
    private String docIdType;

    @Column(name = "doc_id_size")
    private Long docIdSize;

    @Column(name = "doc_addr_name")
    private String docAddrName;

    @Column(name = "doc_addr_type")
    private String docAddrType;

    @Column(name = "doc_addr_size")
    private Long docAddrSize;

}


