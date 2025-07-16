package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartnerRequest;
import com.nic.vat.registration.model.DealerPartner;
import com.nic.vat.registration.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @PostMapping("/partner")
    public ResponseEntity<?> savePartner(@RequestBody PartnerRequest request) {
        Map<String, Object> response = new HashMap<>();

        boolean saved = partnerService.savePartner(request);
        if (saved) {
            response.put("success", true);
            response.put("message", "Partner/contact details saved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid input");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/partner")
    public ResponseEntity<?> getPartners(@RequestParam("applicationNumber") String applicationNumber) {
        List<PartnerResponse> partners = partnerService.getPartnersByAckNo(applicationNumber);
        return ResponseEntity.ok(partners);
    }

}

