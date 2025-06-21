package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // This class handles web requests
@RequestMapping("/api/dealer")
public class DealerMasterController {

    @Autowired
    private DealerMasterRepository repo;

    // POST API to save form data
    @PostMapping("/form1")
    public ResponseEntity<DealerMaster> submitForm(@RequestBody DealerMaster dealer) {
        DealerMaster saved = repo.save(dealer);   // Save to DB
        return ResponseEntity.ok(saved);          // Return saved data
    }
}

