package com.nic.vat.registration.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DealerBankInfoId implements Serializable {
    private BigDecimal ackNo;
    private String branchCode;
    private String accountNumber;
}

