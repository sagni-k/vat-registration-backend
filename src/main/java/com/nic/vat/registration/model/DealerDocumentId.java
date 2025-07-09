package com.nic.vat.registration.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DealerDocumentId implements Serializable {
    private BigDecimal tinNo;
    private BigDecimal ackNo;
    private BigDecimal docNo;
}
