package com.nic.vat.registration.service;

import com.nic.vat.registration.model.dto.PartARequest;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class PartAService {

    @Autowired
    private DealerMasterRepository dealerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DealerMaster savePartA(PartARequest request) {
        DealerMaster dealer = new DealerMaster();

        // Generate ACK No and Date
        dealer.setAckNo(generateAckNo());
        dealer.setAckDate(LocalDate.now());

        // Generate and encode password
        String plainPassword = generatePassword(); // For now, basic fixed or random password
        dealer.setPassword(passwordEncoder.encode(plainPassword));

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
            // Consider mapping district name to code if needed
        }

        // Save dealer
        DealerMaster savedDealer = dealerRepo.save(dealer);

        // Store plain password temporarily for response (optional — can use DTO)
        savedDealer.setPassword(plainPassword); // NOTE: Don't log or expose this in production

        return savedDealer;
    }

    private BigDecimal generateAckNo() {
        long number = 202400000000L + new Random().nextInt(999999); // Dummy logic
        return BigDecimal.valueOf(number);
    }

    private String generatePassword() {
        return "XyZ@1234"; // You can replace with random password generator
    }
}

