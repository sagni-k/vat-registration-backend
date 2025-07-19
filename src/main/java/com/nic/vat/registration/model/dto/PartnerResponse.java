package com.nic.vat.registration.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartnerResponse {
    private String partnerType;
    private String name;
    private String fathersName;
    private String dateOfBirth;
    private String designation;
    private String qualification;
    private String pan;
    private String presentAddress;
    private String area;
    private String village;
    private String permanentAddress;

    private ContactDTO contact;
    private BigDecimal interestPercent;
    private PartnershipDatesDTO partnershipDates;
    private ElectoralDetailsDTO electoralDetails;
    private UploadedDocument uploadedDocument;

    @Data
    public static class ContactDTO {
        private String telephone;
        private String fax;
        private String email;
    }

    @Data
    public static class PartnershipDatesDTO {
        private String entryDate;
        private String exitDate;
    }

    @Data
    public static class ElectoralDetailsDTO {
        private String voterId;
        private String residentialCertNo;
    }

    @Data
    public static class UploadedDocument {
        private DocumentMeta idProof;
        private DocumentMeta addressProof;
    }
    @Data
    public static class DocumentMeta {
        private String name;
        private String type;
        private long size;
    }
}
