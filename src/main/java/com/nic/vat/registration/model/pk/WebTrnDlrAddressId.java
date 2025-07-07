
package com.nic.vat.registration.model.pk;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebTrnDlrAddressId implements Serializable {
    private BigDecimal tinNo;
    private BigDecimal ackNo;
    private BigDecimal sno;
}
