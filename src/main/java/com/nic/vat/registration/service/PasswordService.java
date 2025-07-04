package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
}
