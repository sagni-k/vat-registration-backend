package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.AdditionalBusinessPlaceRequest;
import com.nic.vat.registration.service.DealerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class DealerAddressController {

    @Autowired
    private DealerAddressService dealerAddressService;

    @PostMapping("/additional-business-place")
    public ResponseEntity<?> saveAdditionalBusinessPlace(@RequestBody AdditionalBusinessPlaceRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            dealerAddressService.saveAdditionalBusinessPlace(request);
            response.put("success", true);
            response.put("message", "Additional business place saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid input: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}