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


@Service
public class PartBService {

    @Autowired
    private DealerMasterRepository dealerRepo;

    @Autowired
    private DealerAddressRepository addressRepo;

    public boolean savePartB(PartBRequest request) {
        BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());

        DealerMaster dealer = dealerRepo.findById(ackNo).orElse(null);
        if (dealer == null) return false;

        // Update permanent address
        if (request.getPermanentAddress() != null) {
            dealer.setPermAddr(request.getPermanentAddress().getAddressLine());
            dealer.setPermPlace(request.getPermanentAddress().getPlace());
            dealer.setPermDistCd(new BigDecimal(request.getPermanentAddress().getDistCd()));
            dealer.setPermStCode(request.getPermanentAddress().getStCode());
            dealer.setPermPin(new BigDecimal(request.getPermanentAddress().getPin()));
        }

        // Update residential address
        if (request.getResidentialAddress() != null) {
            dealer.setResiAdd1(request.getResidentialAddress().getAddressLine());
            dealer.setResiPlace(request.getResidentialAddress().getPlace());
            dealer.setResiDistCd(new BigDecimal(request.getResidentialAddress().getDistCd()));
            dealer.setResiStCode(request.getResidentialAddress().getStCode());
            dealer.setResiPin(new BigDecimal(request.getResidentialAddress().getPin()));
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
                addr.setDistCd(new BigDecimal(dto.getDistCd()));
                addr.setStCode(dto.getStCode());
                addr.setPin(new BigDecimal(dto.getPin()));
                addr.setPhone(dto.getPhone());
                addresses.add(addr);
            }
            addressRepo.saveAll(addresses);
        }
        dealer.setActivityCode(request.getEconomicActivity().getActivityCode());
        dealer.setCommodityName(request.getCommodity().getName());
        dealer.setCommodityDescription(request.getCommodity().getDescription());
        dealer.setFirstTaxableSaleDate(LocalDate.parse(request.getFirstTaxableSaleDate()));
        dealer.setVatOption(request.getVatOption());
        dealer.setEstimatedTurnover(request.getEstimatedTurnover());
        dealer.setFilingFrequency(request.getFilingFrequency());

        return true;
    }
}

