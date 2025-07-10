package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartCRequest;
import com.nic.vat.registration.service.DealerRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://vat-registration-frontend.vercel.app"
})

public class DealerRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(DealerRegistrationController.class);

    @Autowired
    private DealerRegistrationService registrationService;

    @PostMapping("/part-c")
    public ResponseEntity<Map<String, Object>> savePartC(@RequestBody PartCRequest request) {
        logger.info("Received Part-C save request for applicationNumber: {}", request.getApplicationNumber());
        Map<String, Object> response = registrationService.savePartC(request);
        logger.info("Response for Part-C save: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/part-c")
    public ResponseEntity<?> getPartC(@RequestParam("applicationNumber") String applicationNumber) {
        logger.info("Received GET request for Part-C with applicationNumber: {}", applicationNumber);
        return ResponseEntity.ok(registrationService.getPartCByAckNo(applicationNumber));
    }

}
