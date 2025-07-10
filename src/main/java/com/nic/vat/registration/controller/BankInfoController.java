package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.BankInfoRequest;
import com.nic.vat.registration.service.BankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "*")
public class BankInfoController {

    @Autowired
    private BankInfoService bankInfoService;

    @PostMapping("/bank-info")
    public ResponseEntity<?> saveBankInfo(@RequestBody BankInfoRequest request) {
        try {
            bankInfoService.saveBankInfo(request);
            return ResponseEntity.ok().body(
                    new ApiResponse(true, "Bank info saved successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Invalid input: " + e.getMessage())
            );
        }
    }

    record ApiResponse(boolean success, String message) {}

    @GetMapping("/bank-info")
    public ResponseEntity<?> getBankInfo(@RequestParam("applicationNumber") String applicationNumber) {
        return ResponseEntity.ok(bankInfoService.getBankInfoByAckNo(applicationNumber));
    }


}

