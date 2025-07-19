package com.nic.vat.registration.model.dto;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Data
public class PartBRequest {
    private String applicationNumber;
    private String statutoryAuthority;

    private PermanentAddressDTO permanentAddress;
    private ResidentialAddressDTO residentialAddress;

    private List<BranchAddressDTO> branchAddresses;
    private EconomicActivityDTO economicActivity;
    private CommodityDTO commodity;

    private String firstTaxableSaleDate;
    private String vatOption;
    private BigDecimal estimatedTurnover;
    private String filingFrequency;

    @Data
    public static class BranchAddressDTO {
        private String name;
        private String addr1;
        private String addr2;
        private String place;
        private String distCd;
        private String stCode;
        private String pin;
        private String phone;
    }
}


