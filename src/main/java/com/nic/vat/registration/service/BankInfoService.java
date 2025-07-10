package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerBankInfo;
import com.nic.vat.registration.model.dto.BankInfoRequest;
import com.nic.vat.registration.repository.DealerBankInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public List<DealerBankInfo> getBankInfoByAckNo(String ackNo) {
        return bankRepo.findByAckNo(new BigDecimal(ackNo));
    }

}

