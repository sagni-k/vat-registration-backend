package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerAddressRepository extends JpaRepository<DealerAddress, Long> {
}
