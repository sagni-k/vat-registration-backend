package com.nic.vat.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "web_trn_dlr_docs", schema = "tvat")
@IdClass(DealerDocumentId.class)
@Data
public class DealerDocument {

    @Id
    @Column(name = "tin_no")
    private BigDecimal tinNo;

    @Id
    @Column(name = "ack_no")
    private BigDecimal ackNo;

    @Id
    @Column(name = "doc_no")
    private BigDecimal docNo;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_code")
    private Integer docCode;

    @Column(name = "doc_file")
    private byte[] docFile;

    @Column(name = "doc_size")
    private String docSize;

    @Column(name = "partner_sl_no")
    private Integer partnerSlNo;

    @Column(name = "entered_by")
    private String enteredBy;

    @Column(name = "entered_dt")
    private LocalDate enteredDt;

    @Column(name = "filetype")
    private String filetype;

    @Column(name = "filename")
    private String filename;

    @Column(name = "other_doc_desc")
    private String otherDocDesc;

    @Column(name = "nature_amendment")
    private String natureAmendment;
}