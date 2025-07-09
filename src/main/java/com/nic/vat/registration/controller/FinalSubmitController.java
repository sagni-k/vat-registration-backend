package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.FinalSubmitRequest;
import com.nic.vat.registration.service.FinalSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class FinalSubmitController {

    @Autowired
    private FinalSubmitService finalSubmitService;

    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody FinalSubmitRequest request) {
        Map<String, Object> response = new HashMap<>();

        boolean success = finalSubmitService.finalizeRegistration(request.getApplicationNumber());

        if (success) {
            response.put("success", true);
            response.put("message", "Registration submitted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Cannot submit. Missing required sections or application not found.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}