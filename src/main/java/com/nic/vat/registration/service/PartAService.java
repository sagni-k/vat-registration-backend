package com.nic.vat.registration.service;

import com.nic.vat.registration.model.dto.PartARequest;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class PartAService {

    @Autowired
    private DealerMasterRepository dealerRepo;

    public DealerMaster savePartA(PartARequest request) {
        DealerMaster dealer = new DealerMaster();

        // Generate ACK No and Date
        dealer.setAckNo(generateAckNo());
        dealer.setAckDate(LocalDate.now());

        // Map values from PartARequest to DealerMaster
        dealer.setTradName(request.getTradingName());
        dealer.setApplNameS(request.getApplicantName());
        dealer.setFathName(request.getFathersName());
        dealer.setDtBirth(LocalDate.parse(request.getDateOfBirth()));
        dealer.setSex(request.getGender());
        dealer.setPan(request.getPan());
        dealer.setOffice(request.getOffice());
        dealer.setRegType(request.getTypeOfRegistration());

        // Contact and Address fields
        if (request.getContact() != null) {
            dealer.setTelephone(request.getContact().getTelephone());
            dealer.setFax(request.getContact().getFax());
            dealer.setEmail(request.getContact().getEmail());
            if (request.getContact().getMobile() != null) {
                dealer.setMobile(new BigDecimal(request.getContact().getMobile()));
            }
        }

        if (request.getAddress() != null) {
            dealer.setPermAddr(request.getAddress().getRoomNo());
            dealer.setPermPlace(request.getAddress().getVillage());
            if (request.getAddress().getPinCode() != null) {
                dealer.setPermPin(new BigDecimal(request.getAddress().getPinCode()));
            }
            // You may want to convert district string to a district code manually or via lookup
        }

        return dealerRepo.save(dealer);
    }

    private BigDecimal generateAckNo() {
        long number = 202400000000L + new Random().nextInt(999999); // Dummy logic
        return BigDecimal.valueOf(number);
    }
}
