package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerMaster;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DealerMasterRepository extends JpaRepository<DealerMaster, BigDecimal> {
    List<DealerMaster> findByApplNameSAndFathNameAndDtBirth(String applNameS, String fathName, LocalDate dtBirth);
    Optional<DealerMaster> findByAckNo(BigDecimal ackNo);
}
