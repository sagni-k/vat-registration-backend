package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerMaster;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;



public interface DealerMasterRepository extends JpaRepository<DealerMaster, BigDecimal> {
    // No custom methods for now
}
