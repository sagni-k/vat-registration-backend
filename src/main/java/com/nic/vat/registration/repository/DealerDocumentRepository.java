package com.nic.vat.registration.repository;

import com.nic.vat.registration.model.DealerDocument;
import com.nic.vat.registration.model.DealerDocumentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerDocumentRepository extends JpaRepository<DealerDocument, DealerDocumentId> {
}