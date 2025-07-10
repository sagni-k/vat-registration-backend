package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartBRequest;
import com.nic.vat.registration.service.PartBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class DealerPartBController {

    @Autowired
    private PartBService partBService;

    @PostMapping("/part-b")
    public ResponseEntity<?> savePartB(@RequestBody PartBRequest request) {
        boolean success = partBService.savePartB(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Part B saved successfully." : "Application number not found");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/part-b")
    public ResponseEntity<?> getPartB(@RequestParam("applicationNumber") String applicationNumber) {
        return ResponseEntity.ok(partBService.getPartBByAckNo(applicationNumber));
    }
}

