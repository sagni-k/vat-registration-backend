package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.User;
import com.nic.vat.registration.model.dto.SignupRequest;
import com.nic.vat.registration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            User user = userService.registerUser(signupRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully!");
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Registration failed. Please try again.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.existsByUsername(username);
        response.put("exists", exists);
        response.put("message", exists ? "Username is already taken" : "Username is available");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.existsByEmail(email);
        response.put("exists", exists);
        response.put("message", exists ? "Email is already registered" : "Email is available");
        return ResponseEntity.ok(response);
    }
}