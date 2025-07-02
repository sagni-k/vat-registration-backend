package com.nic.vat.registration.model.dto;

import lombok.Data;

@Data
public class BankInfoRequest {
    private String applicationNumber;
    private String bankName;
    private String branchAddress;
    private String accountNumber;
    private String branchCode;
    private String accountType;
}

