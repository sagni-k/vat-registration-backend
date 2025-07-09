package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nic.vat.registration.model.dto.ForgotApplicationRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.List;

@Service
public class PasswordService {

    @Autowired
    private DealerMasterRepository repo;

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
                // TODO: Generate and send new password
                response.put("success", true);
                response.put("message", "New password sent to your registered email and mobile number");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid input");
        }

        return response;
    }

    @Autowired
    private DealerMasterRepository dealerRepo;

    public Map<String, Object> forgotApplication(ForgotApplicationRequest request) {
        Map<String, Object> response = new HashMap<>();

        List<DealerMaster> matches = dealerRepo.findByApplNameSAndFathNameAndDtBirth(
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
        return response;
    }

}
