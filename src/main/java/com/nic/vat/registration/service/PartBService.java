package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerAddress;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.PartBRequest;
import com.nic.vat.registration.repository.DealerAddressRepository;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDate;

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
            return false;
        }

        DealerMaster dealer = dealerRepo.findById(ackNo).orElse(null);
        if (dealer == null) return false;

        dealer.setStatutoryAuthority(request.getStatutoryAuthority());

        if (request.getPermanentAddress() != null) {
            var perm = request.getPermanentAddress();
            dealer.setPermAddr(perm.getAddressLine());
            dealer.setPermPlace(perm.getPlace());
            dealer.setPermStCode(perm.getStCode());
            dealer.setPermCountry(perm.getCountry());
            if (isValid(perm.getDistCd()))
                dealer.setPermDistCd(new BigDecimal(perm.getDistCd()));
            if (isValid(perm.getPin()))
                dealer.setPermPin(new BigDecimal(perm.getPin()));
        }

        if (request.getResidentialAddress() != null) {
            var resi = request.getResidentialAddress();
            dealer.setResiAdd1(resi.getAddressLine());
            dealer.setResiPlace(resi.getPlace());
            dealer.setResiStCode(resi.getStCode());
            dealer.setResiCountry(resi.getCountry());
            if (isValid(resi.getDistCd()))
                dealer.setResiDistCd(new BigDecimal(resi.getDistCd()));
            if (isValid(resi.getPin()))
                dealer.setResiPin(new BigDecimal(resi.getPin()));
        }

        if (request.getEconomicActivity() != null) {
            dealer.setActivityCode(request.getEconomicActivity().getActivityCode());
            if (request.getEconomicActivity().getRoles() != null) {
                dealer.setEconomicRoles(String.join(",", request.getEconomicActivity().getRoles()));
            }
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
        BigDecimal ack = new BigDecimal(ackNo);
        DealerMaster dealer = dealerRepo.findById(ack).orElse(null);
        List<DealerAddress> addresses = addressRepo.findByAckNo(ack);

        Map<String, Object> response = new HashMap<>();

        if (dealer == null) {
            response.put("success", false);
            response.put("message", "Dealer not found");
            return response;
        }

        Map<String, Object> permanentAddress = new HashMap<>();
        permanentAddress.put("street", dealer.getPermAddr());
        permanentAddress.put("city", dealer.getPermPlace());
        permanentAddress.put("district", dealer.getPermDistCd());
        permanentAddress.put("state", dealer.getPermStCode());
        permanentAddress.put("country", dealer.getPermCountry());
        permanentAddress.put("pinCode", dealer.getPermPin());

        Map<String, Object> residentialAddress = new HashMap<>();
        residentialAddress.put("street", dealer.getResiAdd1());
        residentialAddress.put("city", dealer.getResiPlace());
        residentialAddress.put("district", dealer.getResiDistCd());
        residentialAddress.put("state", dealer.getResiStCode());
        residentialAddress.put("country", dealer.getResiCountry());
        residentialAddress.put("pinCode", dealer.getResiPin());

        Map<String, Object> commodity = new HashMap<>();
        commodity.put("name", dealer.getCommodityName());
        commodity.put("description", dealer.getCommodityDescription());

        Map<String, Object> economicActivity = new HashMap<>();
        economicActivity.put("activityCode", dealer.getActivityCode());
        economicActivity.put("roles", dealer.getEconomicRoles() != null
                ? Arrays.asList(dealer.getEconomicRoles().split(","))
                : new ArrayList<>());

        response.put("success", true);
        response.put("statutoryAuthority", dealer.getStatutoryAuthority());
        response.put("permanentAddress", permanentAddress);
        response.put("residentialAddress", residentialAddress);
        response.put("commodity", commodity);
        response.put("economicActivity", economicActivity);
        response.put("firstTaxableSaleDate", dealer.getFirstTaxableSaleDate());
        response.put("vatOption", dealer.getVatOption());
        response.put("estimatedTurnover", dealer.getEstimatedTurnover());
        response.put("filingFrequency", dealer.getFilingFrequency());
        response.put("branchAddresses", addresses);

        return response;
    }
}
