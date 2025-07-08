package com.nic.vat.registration.service;

import com.nic.vat.registration.config.security.JwtUtil;
import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.model.dto.LoginRequest;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private DealerMasterRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        // CAPTCHA check — mock for now
        if (!"A9X3Z".equalsIgnoreCase(request.getCaptcha())) {
            response.put("success", false);
            response.put("message", "Invalid captcha");
            return response;
        }

        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());
            DealerMaster dealer = repo.findById(ackNo).orElse(null);

            if (dealer == null) {
                response.put("success", false);
                response.put("message", "Invalid application number");
                return response;
            }

            String hashedPassword = dealer.getPassword();
            if (hashedPassword == null || !passwordEncoder.matches(request.getPassword(), hashedPassword)) {
                response.put("success", false);
                response.put("message", "Invalid password");
                return response;
            }

            // ✅ Generate JWT token
            String token = jwtUtil.generateToken(request.getApplicationNumber());

            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid input format");
        }

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            BigDecimal ackNo = new BigDecimal(username);
            DealerMaster dealer = repo.findById(ackNo)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with application number: " + username));

            return User.builder()
                    .username(dealer.getAckNo().toString())
                    .password(dealer.getPassword() != null ? dealer.getPassword() : "")
                    .authorities(new ArrayList<>())
                    .build();

        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid application number format: " + username);
        }
    }
}



