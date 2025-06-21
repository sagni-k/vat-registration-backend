package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerMasterRepository extends JpaRepository<DealerMaster, Long> {
    // No code needed. Spring Boot auto-generates everything.
}
