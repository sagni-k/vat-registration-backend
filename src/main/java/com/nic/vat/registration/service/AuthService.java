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

        // Find by ackNo — and check password
        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());
            DealerMaster dealer = repo.findById(ackNo).orElse(null);

            if (dealer == null) {
                response.put("success", false);
                response.put("message", "Invalid application number");
            } else {
                // BCrypt password check - using encoded version of "user@123"
                String encodedPassword = "$2a$10$dXJ3SW6G7P4S5CHgFrANjO.oQjKKlvNGaAHNRfb6FMmXCyqOJ0K5i";
                
                if (passwordEncoder.matches(request.getPassword(), encodedPassword)) {
                    // Generate JWT token after successful authentication
                    String token = jwtUtil.generateToken(request.getApplicationNumber());
                    
                    response.put("success", true);
                    response.put("message", "Login successful");
                    response.put("token", token);
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

    /**
     * Loads user details by username for Spring Security authentication
     * 
     * @param username The username (application number) to load
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            BigDecimal ackNo = new BigDecimal(username);
            DealerMaster dealer = repo.findById(ackNo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with application number: " + username));
            
            // Create UserDetails object with dealer information
            // For now, using a mock password - replace with actual encrypted password from DB
            return User.builder()
                .username(dealer.getAckNo().toString())
                .password("$2a$10$dXJ3SW6G7P4S5CHgFrANjO.oQjKKlvNGaAHNRfb6FMmXCyqOJ0K5i") // BCrypt encoded "user@123"
                .authorities(new ArrayList<>()) // Add roles/authorities as needed
                .build();
                
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid application number format: " + username);
        }
    }
}

