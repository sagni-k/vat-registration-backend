package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartCRequest;
import com.nic.vat.registration.service.DealerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "*")
public class DealerRegistrationController {

    @Autowired
    private DealerRegistrationService registrationService;

    // Existing Part A/B methods...

    @PostMapping("/part-c")
    public ResponseEntity<Map<String, Object>> savePartC(@RequestBody PartCRequest request) {
        Map<String, Object> response = registrationService.savePartC(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/part-c")
    public ResponseEntity<?> getPartC(@RequestParam("applicationNumber") String applicationNumber) {
        return ResponseEntity.ok(registrationService.getPartCByAckNo(applicationNumber));
    }

}
