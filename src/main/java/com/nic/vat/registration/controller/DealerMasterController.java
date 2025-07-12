package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartARequest;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.service.PartAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class DealerMasterController {

    @Autowired
    private PartAService partAService;

    @PostMapping("/part-a")
    public ResponseEntity<?> savePartA(@RequestBody PartARequest request) {
        try {
            DealerMaster saved = partAService.savePartA(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("applicationNumber", saved.getAckNo().toString());

            // Return the actual generated password from service
            response.put("password", saved.getPassword());

            response.put("message", "Registration successful. Check your email and mobile for credentials.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/part-a")
    public ResponseEntity<?> getPartA(@RequestParam("applicationNumber") String applicationNumber) {
        Map<String, Object> partA = partAService.getPartAFields(applicationNumber);
        if (partA == null) {
            return ResponseEntity.status(404).body(Map.of("message", "No data found"));
        }
        return ResponseEntity.ok(partA);
    }

    @PutMapping("/part-a")
    public ResponseEntity<?> updatePartA(@RequestBody PartARequest request) {
        boolean success = partAService.updatePartA(request);
        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid application number or record not found"));
        }
        return ResponseEntity.ok(Map.of("success", true, "message", "Part-A data updated successfully"));
    }



}

