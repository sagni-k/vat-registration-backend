package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerMaster;
import com.nic.vat.registration.repository.DealerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Custom UserDetailsService implementation for loading user details from the database
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DealerMasterRepository dealerMasterRepository;

    /**
     * Load user details by username (application number in this case)
     * 
     * @param username The username (application number) to load
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Convert username to BigDecimal (assuming it's the application number)
            BigDecimal ackNo = new BigDecimal(username);
            
            // Find dealer by acknowledgment number
            DealerMaster dealer = dealerMasterRepository.findById(ackNo)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with application number: " + username));
            
            // Create UserDetails with username and default authorities
            // In a real application, you might want to add proper roles/authorities
            return User.builder()
                    .username(dealer.getAckNo().toString())
                    .password("$2a$10$dXJ3SW6G7P4S5CHgFrANjO.oQjKKlvNGaAHNRfb6FMmXCyqOJ0K5i") // Default encoded password
                    .authorities(new ArrayList<>()) // Empty authorities for now
                    .build();
                    
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid application number format: " + username);
        }
    }
}