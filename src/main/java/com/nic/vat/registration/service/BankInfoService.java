package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerBankInfo;
import com.nic.vat.registration.model.dto.BankInfoRequest;
import com.nic.vat.registration.repository.DealerBankInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BankInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BankInfoService.class);

    @Autowired
    private DealerBankInfoRepository bankRepo;

    public void saveBankInfo(BankInfoRequest request) {
        logger.info("Saving bank info for applicationNumber: {}", request.getApplicationNumber());

        try {
            DealerBankInfo bankInfo = new DealerBankInfo();
            bankInfo.setAckNo(new BigDecimal(request.getApplicationNumber()));
            bankInfo.setBankName(request.getBankName());
            bankInfo.setBranchAddress(request.getBranchAddress());
            bankInfo.setAccountNumber(request.getAccountNumber());
            bankInfo.setBranchCode(request.getBranchCode());
            bankInfo.setAccountType(request.getAccountType());

            bankRepo.save(bankInfo);
            logger.info("Bank info saved successfully for applicationNumber: {}", request.getApplicationNumber());
        } catch (Exception e) {
            logger.error("Failed to save bank info for applicationNumber: {} - {}", request.getApplicationNumber(), e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getBankInfoByAckNo(String ackNo) {
        logger.info("Fetching bank info for applicationNumber: {}", ackNo);
        Map<String, Object> response = new HashMap<>();

        try {
            BigDecimal ack = new BigDecimal(ackNo);
            DealerBankInfo bank = bankRepo.findFirstByAckNo(ack);

            if (bank == null) {
                logger.warn("No bank info found for applicationNumber: {}", ackNo);
                response.put("error", "No bank info found for given application number");
                return response;
            }

            response.put("bankName", bank.getBankName());
            response.put("accountNumber", bank.getAccountNumber());
            response.put("accountType", bank.getAccountType());
            logger.info("Bank info fetched successfully for applicationNumber: {}", ackNo);

        } catch (Exception e) {
            logger.error("Invalid application number format for bank info: {} - {}", ackNo, e.getMessage());
            response.put("error", "Invalid application number format");
        }

        return response;
    }

}
