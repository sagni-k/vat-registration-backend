package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.math.BigDecimal;

public interface DealerAddressRepository extends JpaRepository<DealerAddress, Long> {
    List<DealerAddress> findByAckNo(BigDecimal ackNo);
}
