package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "web_trn_dlr_bank", schema = "tvat")
@IdClass(DealerBankInfoId.class)
@Data
public class DealerBankInfo {

    @Id
    @Column(name = "ack_no")
    private BigDecimal ackNo;

    @Id
    @Column(name = "bank_code")
    private String branchCode;

    @Id
    @Column(name = "bank_acc_no")
    private String accountNumber;

    @Column(name = "bank_bran_name")
    private String branchAddress;

    @Column(name = "bank_acc_type")
    private String accountType;

    // Optional: if NIC confirms
    @Column(name = "bank_no")
    private String bankName;
}

