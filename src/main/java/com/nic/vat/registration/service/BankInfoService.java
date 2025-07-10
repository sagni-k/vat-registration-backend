package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerBankInfo;
import com.nic.vat.registration.model.dto.BankInfoRequest;
import com.nic.vat.registration.repository.DealerBankInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BankInfoService {

    @Autowired
    private DealerBankInfoRepository bankRepo;

    public void saveBankInfo(BankInfoRequest request) {
        DealerBankInfo bankInfo = new DealerBankInfo();
        bankInfo.setAckNo(new BigDecimal(request.getApplicationNumber()));
        bankInfo.setBankName(request.getBankName());
        bankInfo.setBranchAddress(request.getBranchAddress());
        bankInfo.setAccountNumber(request.getAccountNumber());
        bankInfo.setBranchCode(request.getBranchCode());
        bankInfo.setAccountType(request.getAccountType());

        bankRepo.save(bankInfo);
    }

    public Map<String, Object> getBankInfoByAckNo(String ackNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            BigDecimal ack = new BigDecimal(ackNo);

            DealerBankInfo bank = bankRepo.findFirstByAckNo(ack); // ✅ assumes one record

            if (bank == null) {
                response.put("error", "No bank info found for given application number");
                return response;
            }

            response.put("bankName", bank.getBankName());
            response.put("accountNumber", bank.getAccountNumber());
            response.put("accountType", bank.getAccountType());

        } catch (Exception e) {
            response.put("error", "Invalid application number format");
        }

        return response;
    }

}

