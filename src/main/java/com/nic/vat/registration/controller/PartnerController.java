package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.PartnerRequest;
import com.nic.vat.registration.model.dto.PartnerResponse;
import com.nic.vat.registration.service.PartnerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @PostMapping("/partner")
    public ResponseEntity<?> savePartner(
            @RequestPart("data") PartnerRequest request,
            @RequestPart(value = "idProof", required = false) MultipartFile idProofFile,
            @RequestPart(value = "addressProof", required = false) MultipartFile addrProofFile
    ) {
        Map<String, Object> response = new HashMap<>();
        boolean saved = partnerService.savePartner(request, idProofFile, addrProofFile);
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

    @GetMapping("/partner/id-proof")
    public void getIdProofFile(@RequestParam("applicationNumber") String ackNo,
                               @RequestParam("pan") String pan,
                               HttpServletResponse response) throws IOException {
        partnerService.writeFileToResponse(ackNo, pan, "id", response);
    }

    @GetMapping("/partner/address-proof")
    public void getAddressProofFile(@RequestParam("applicationNumber") String ackNo,
                                    @RequestParam("pan") String pan,
                                    HttpServletResponse response) throws IOException {
        partnerService.writeFileToResponse(ackNo, pan, "address", response);
    }
}
