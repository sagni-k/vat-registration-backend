package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DealerPartnerRepository extends JpaRepository<DealerPartner, Long> {

    @Query("SELECT COALESCE(MAX(p.sno), 0) FROM DealerPartner p WHERE p.ackNo = :ackNo")
    BigDecimal findMaxSnoByAckNo(BigDecimal ackNo);

    List<DealerPartner> findByAckNo(BigDecimal ackNo);
}


