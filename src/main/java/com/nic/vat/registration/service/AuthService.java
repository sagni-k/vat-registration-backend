package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.LoginRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private DealerMasterRepository repo;

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        // CAPTCHA check — mock for now
        if (!"A9X3Z".equalsIgnoreCase(request.getCaptcha())) {
            response.put("success", false);
            response.put("message", "Invalid captcha");
            return response;
        }

        // Find by ackNo — and check password
        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());
            DealerMaster dealer = repo.findById(ackNo).orElse(null);

            if (dealer == null) {
                response.put("success", false);
                response.put("message", "Invalid application number");
            } else {
                // MOCK password logic — you can replace this with DB check later
                if ("user@123".equals(request.getPassword())) {
                    response.put("success", true);
                    response.put("message", "Login successful");
                } else {
                    response.put("success", false);
                    response.put("message", "Invalid password");
                }
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid input format");
        }

        return response;
    }
}

