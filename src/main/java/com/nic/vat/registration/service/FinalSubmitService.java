package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FinalSubmitService {

    @Autowired
    private DealerMasterRepository repo;

    public boolean finalizeRegistration(String ackNo) {
        Optional<DealerMaster> optional = repo.findById(new BigDecimal(ackNo));
        if (optional.isEmpty()) {
            return false;
        }

        DealerMaster dealer = optional.get();

        // Simple check — you can add more validations for completeness
        dealer.setTrnStatus("SUB");       // Marks submitted
        dealer.setDlrStatus(BigDecimal.valueOf(2)); // Status 2 = submitted (example)

        repo.save(dealer);
        return true;
    }
}