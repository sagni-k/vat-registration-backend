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
            response.put("password", "XyZ@1234"); // dummy password generation for now
            response.put("message", "Registration successful. Check your email and mobile for credentials.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(error);
        }
    }

}
