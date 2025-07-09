package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.model.dto.ForgotApplicationRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class PasswordService {

    @Autowired
    private DealerMasterRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, Object> forgotPassword(ForgotPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (!"B2F7P".equalsIgnoreCase(request.getCaptcha())) {
            response.put("success", false);
            response.put("message", "Invalid captcha");
            return response;
        }

        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());
            DealerMaster dealer = repo.findById(ackNo).orElse(null);

            if (dealer == null || dealer.getDtBirth() == null ||
                    !dealer.getDtBirth().toString().equals(request.getDateOfBirth())) {
                response.put("success", false);
                response.put("message", "Invalid application number, date of birth, or captcha");
            } else {
                // ✅ Generate new random password
                String newPassword = generatePassword();
                String hashedPassword = passwordEncoder.encode(newPassword);

                // ✅ Save hashed password to DB
                dealer.setPassword(hashedPassword);
                repo.save(dealer);

                // ✅ Return plain password in response
                response.put("success", true);
                response.put("message", "New password generated successfully");
                response.put("newPassword", newPassword);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid input");
        }

        return response;
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$!";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return password.toString();
    }

    public Map<String, Object> forgotApplication(ForgotApplicationRequest request) {
        Map<String, Object> response = new HashMap<>();

        List<DealerMaster> matches = repo.findByApplNameSAndFathNameAndDtBirth(
                request.getApplicantName(),
                request.getFathersName(),
                LocalDate.parse(request.getDateOfBirth())
        );

        if (matches.isEmpty()) {
            response.put("success", false);
            response.put("message", "No matching record found");
            return response;
        }

        DealerMaster dealer = matches.get(0); // assuming first match

        response.put("success", true);
        response.put("message", "Application number and password sent to your registered email and mobile number");
        response.put("applicationNumber", dealer.getAckNo().toPlainString());
        return response;
    }
}

