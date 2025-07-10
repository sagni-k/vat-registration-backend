package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.BankInfoRequest;
import com.nic.vat.registration.service.BankInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://vat-registration-frontend.vercel.app"
})

public class BankInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BankInfoController.class);

    @Autowired
    private BankInfoService bankInfoService;

    @PostMapping("/bank-info")
    public ResponseEntity<?> saveBankInfo(@RequestBody BankInfoRequest request) {
        logger.info("Saving bank info for applicationNumber: {}", request.getApplicationNumber());
        try {
            bankInfoService.saveBankInfo(request);
            logger.info("Bank info saved successfully for applicationNumber: {}", request.getApplicationNumber());
            return ResponseEntity.ok().body(new ApiResponse(true, "Bank info saved successfully"));
        } catch (Exception e) {
            logger.error("Failed to save bank info: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid input: " + e.getMessage()));
        }
    }

    @GetMapping("/bank-info")
    public ResponseEntity<?> getBankInfo(@RequestParam("applicationNumber") String applicationNumber) {
        logger.info("Fetching bank info for applicationNumber: {}", applicationNumber);
        return ResponseEntity.ok(bankInfoService.getBankInfoByAckNo(applicationNumber));
    }

    record ApiResponse(boolean success, String message) {}
}
