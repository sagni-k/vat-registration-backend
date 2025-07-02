package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerBankInfo;
import com.nic.vat.registration.model.DealerBankInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerBankInfoRepository extends JpaRepository<DealerBankInfo, DealerBankInfoId> {
}
