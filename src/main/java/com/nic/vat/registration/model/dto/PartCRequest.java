package com.nic.vat.registration.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PartCRequest {

    private String applicationNumber;

    private String centralExciseRegNo;

    private String tradeLicenseNo;
    private LocalDate tradeLicenseIssueDate;
    private LocalDate tradeLicenseRenewalDate;

    private String accountLanguage;
    private String accountingYearFrom;
    private String accountingYearTo;

    private BigDecimal saleLastQuarter;
    private BigDecimal saleLastYear;

    private ShopLicense shopLicense;
    private FoodLicense foodLicense;

    private Boolean isIndianCitizen;

    private Declaration declaration;

    @Data
    public static class ShopLicense {
        private String licenseNo;
        private LocalDate issueDate;
    }

    @Data
    public static class FoodLicense {
        private String licenseNo;
        private LocalDate issueDate;
    }

    @Data
    public static class Declaration {
        private String applicantName;
        private String designation;
    }
}

