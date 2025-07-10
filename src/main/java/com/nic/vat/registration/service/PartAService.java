package com.nic.vat.registration.service;

import com.nic.vat.registration.model.dto.PartARequest;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
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
        String plainPassword = generatePassword();
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
        }

        // Save dealer
        DealerMaster savedDealer = dealerRepo.save(dealer);

        // Store plain password temporarily for response
        savedDealer.setPassword(plainPassword); // only for response

        return savedDealer;
    }

    private BigDecimal generateAckNo() {
        long number = 202400000000L + new Random().nextInt(999999); // Dummy logic
        return BigDecimal.valueOf(number);
    }

    private String generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "@#$%&";
        String allChars = upper + lower + digits + symbols;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Ensure one of each type
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(symbols.charAt(random.nextInt(symbols.length())));

        // Fill the rest (total 10 characters)
        for (int i = 4; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }
    public DealerMaster getPartAByAckNo(String ackNo) {
        return dealerRepo.findByAckNo(new BigDecimal(ackNo)).orElse(null);
    }

}


