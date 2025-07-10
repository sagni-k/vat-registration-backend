package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerAddress;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.PartBRequest;
import com.nic.vat.registration.repository.DealerAddressRepository;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PartBService {

    @Autowired
    private DealerMasterRepository dealerRepo;

    @Autowired
    private DealerAddressRepository addressRepo;

    private boolean isValid(String val) {
        return val != null && !val.trim().isEmpty();
    }

    public boolean savePartB(PartBRequest request) {
        BigDecimal ackNo;
        try {
            ackNo = new BigDecimal(request.getApplicationNumber());
        } catch (Exception e) {
            return false; // Invalid application number format
        }

        DealerMaster dealer = dealerRepo.findById(ackNo).orElse(null);
        if (dealer == null) return false;

        // Update permanent address
        if (request.getPermanentAddress() != null) {
            var perm = request.getPermanentAddress();
            dealer.setPermAddr(perm.getAddressLine());
            dealer.setPermPlace(perm.getPlace());
            dealer.setPermStCode(perm.getStCode());

            if (isValid(perm.getDistCd()))
                dealer.setPermDistCd(new BigDecimal(perm.getDistCd()));
            if (isValid(perm.getPin()))
                dealer.setPermPin(new BigDecimal(perm.getPin()));
        }

        // Update residential address
        if (request.getResidentialAddress() != null) {
            var resi = request.getResidentialAddress();
            dealer.setResiAdd1(resi.getAddressLine());
            dealer.setResiPlace(resi.getPlace());
            dealer.setResiStCode(resi.getStCode());

            if (isValid(resi.getDistCd()))
                dealer.setResiDistCd(new BigDecimal(resi.getDistCd()));
            if (isValid(resi.getPin()))
                dealer.setResiPin(new BigDecimal(resi.getPin()));
        }

        // Update economic activity and commodity
        if (request.getEconomicActivity() != null) {
            dealer.setActivityCode(request.getEconomicActivity().getActivityCode());
        }
        if (request.getCommodity() != null) {
            dealer.setCommodityName(request.getCommodity().getName());
            dealer.setCommodityDescription(request.getCommodity().getDescription());
        }

        if (isValid(request.getFirstTaxableSaleDate())) {
            try {
                dealer.setFirstTaxableSaleDate(LocalDate.parse(request.getFirstTaxableSaleDate()));
            } catch (Exception ignored) {}
        }

        dealer.setVatOption(request.getVatOption());
        dealer.setFilingFrequency(request.getFilingFrequency());

        if (request.getEstimatedTurnover() != null) {
            dealer.setEstimatedTurnover(request.getEstimatedTurnover());
        }

        dealerRepo.save(dealer);

        // Save branch addresses
        if (request.getBranchAddresses() != null) {
            List<DealerAddress> addresses = new ArrayList<>();
            int sno = 1;
            for (PartBRequest.BranchAddressDTO dto : request.getBranchAddresses()) {
                DealerAddress addr = new DealerAddress();
                addr.setAckNo(ackNo);
                addr.setSno(BigDecimal.valueOf(sno++));
                addr.setName(dto.getName());
                addr.setAddr1(dto.getAddr1());
                addr.setAddr2(dto.getAddr2());
                addr.setPlace(dto.getPlace());
                addr.setStCode(dto.getStCode());
                addr.setPhone(dto.getPhone());

                if (isValid(dto.getDistCd()))
                    addr.setDistCd(new BigDecimal(dto.getDistCd()));
                if (isValid(dto.getPin()))
                    addr.setPin(new BigDecimal(dto.getPin()));

                addresses.add(addr);
            }
            addressRepo.saveAll(addresses);
        }

        return true;
    }
    public Map<String, Object> getPartBByAckNo(String ackNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            BigDecimal ackNoDecimal = new BigDecimal(ackNo);
            DealerMaster dealer = dealerRepo.findById(ackNoDecimal).orElse(null);
            if (dealer == null) {
                response.put("error", "Dealer not found");
                return response;
            }

            response.put("statutoryAuthority", dealer.getRegType()); // or any relevant field
            response.put("vatOption", dealer.getVatOption());
            response.put("estimatedTurnover", dealer.getEstimatedTurnover());
            return response;

        } catch (Exception e) {
            response.put("error", "Invalid application number");
            return response;
        }
    }

}

