package com.nic.vat.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class DealerAddressId implements Serializable {

    private BigDecimal tinNo;
    private BigDecimal ackNo;
    private BigDecimal sno;

    public DealerAddressId() {}

    public DealerAddressId(BigDecimal tinNo, BigDecimal ackNo, BigDecimal sno) {
        this.tinNo = tinNo;
        this.ackNo = ackNo;
        this.sno = sno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealerAddressId that)) return false;
        return Objects.equals(tinNo, that.tinNo) &&
                Objects.equals(ackNo, that.ackNo) &&
                Objects.equals(sno, that.sno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tinNo, ackNo, sno);
    }
}
