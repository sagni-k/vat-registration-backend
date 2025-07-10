package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.PartCRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DealerRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(DealerRegistrationService.class);

    @Autowired
    private DealerMasterRepository dealerRepo;

    public Map<String, Object> savePartC(PartCRequest request) {
        logger.info("Saving Part-C for applicationNumber: {}", request.getApplicationNumber());
        Map<String, Object> response = new HashMap<>();

        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());
            DealerMaster dealer = dealerRepo.findById(ackNo).orElseThrow(() ->
                    new RuntimeException("Dealer not found with application number: " + ackNo));

            dealer.setRegCentralExcise(request.getCentralExciseRegNo());
            dealer.setTradeLicenseNo(request.getTradeLicenseNo());
            dealer.setTradeLicenseIssueDate(request.getTradeLicenseIssueDate());
            dealer.setTradeLicenseRenewalDate(request.getTradeLicenseRenewalDate());
            dealer.setAccYearFrom(request.getAccountingYearFrom());
            dealer.setAccYearTo(request.getAccountingYearTo());
            dealer.setSaleLastQuarter(request.getSaleLastQuarter());
            dealer.setSaleLastYear(request.getSaleLastYear());

            if (request.getShopLicense() != null) {
                dealer.setShopLicenseNo(request.getShopLicense().getLicenseNo());
                dealer.setShopLicenseIssueDate(request.getShopLicense().getIssueDate());
            }

            if (request.getFoodLicense() != null) {
                dealer.setFoodLicenseNo(request.getFoodLicense().getLicenseNo());
                dealer.setFoodLicenseIssueDate(request.getFoodLicense().getIssueDate());
            }

            dealer.setIsIndian(request.getIsIndianCitizen() != null && request.getIsIndianCitizen() ? "Y" : "N");

            dealerRepo.save(dealer);
            logger.info("Part-C saved successfully for applicationNumber: {}", request.getApplicationNumber());

            response.put("success", true);
            response.put("message", "Part C saved successfully.");
        } catch (Exception e) {
            logger.error("Error saving Part C for {}: {}", request.getApplicationNumber(), e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to save Part C: " + e.getMessage());
        }

        return response;
    }

    public Map<String, Object> getPartCByAckNo(String ackNo) {
        logger.info("Fetching Part-C for applicationNumber: {}", ackNo);
        Map<String, Object> response = new HashMap<>();

        try {
            BigDecimal ack = new BigDecimal(ackNo);
            DealerMaster dealer = dealerRepo.findById(ack).orElse(null);

            if (dealer == null) {
                logger.warn("Dealer not found for ackNo: {}", ackNo);
                response.put("error", "Dealer not found");
                return response;
            }

            response.put("centralExciseRegNo", dealer.getRegCentralExcise());
            response.put("tradeLicenseNo", dealer.getTradeLicenseNo());
            response.put("isIndianCitizen", "Y".equalsIgnoreCase(dealer.getIsIndian()));

        } catch (Exception e) {
            logger.error("Error fetching Part C for {}: {}", ackNo, e.getMessage());
            response.put("error", "Invalid application number");
        }

        return response;
    }
}
