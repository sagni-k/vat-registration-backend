package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerBankInfo;
import com.nic.vat.registration.model.DealerBankInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface DealerBankInfoRepository extends JpaRepository<DealerBankInfo, DealerBankInfoId> {
    DealerBankInfo findFirstByAckNo(BigDecimal ackNo);
}
