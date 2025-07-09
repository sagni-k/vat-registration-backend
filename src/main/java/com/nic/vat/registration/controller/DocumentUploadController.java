package com.nic.vat.registration.controller;

import com.nic.vat.registration.service.DocumentUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class DocumentUploadController {

    @Autowired
    private DocumentUploadService documentUploadService;

    @PostMapping("/documents")
    public ResponseEntity<?> uploadDocuments(
            @RequestParam("applicationNumber") String applicationNumber,
            @RequestParam(value = "idProof", required = false) MultipartFile idProof,
            @RequestParam(value = "addressProof", required = false) MultipartFile addressProof,
            @RequestParam(value = "panCard", required = false) MultipartFile panCard,
            @RequestParam(value = "applicantPhoto", required = false) MultipartFile applicantPhoto) {

        Map<String, Object> response = new HashMap<>();

        try {
            documentUploadService.saveDocuments(applicationNumber, idProof, addressProof, panCard, applicantPhoto);
            response.put("success", true);
            response.put("message", "Documents uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid document upload: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

