package com.nic.vat.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class DealerPartnerId implements Serializable {

    private BigDecimal ackNo;
    private BigDecimal sno;

    public DealerPartnerId() {}

    public DealerPartnerId(BigDecimal ackNo, BigDecimal sno) {
        this.ackNo = ackNo;
        this.sno = sno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealerPartnerId that)) return false;
        return ackNo.equals(that.ackNo) && sno.equals(that.sno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ackNo, sno);
    }
}

