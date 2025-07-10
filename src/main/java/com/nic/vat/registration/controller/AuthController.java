package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.LoginRequest;
import com.nic.vat.registration.model.dto.ForgotApplicationRequest;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.service.AuthService;
import com.nic.vat.registration.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Login attempt for applicationNumber: {}", request.getApplicationNumber());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        logger.info("Forgot password request received for applicationNumber: {}", request.getApplicationNumber());
        return ResponseEntity.ok(passwordService.forgotPassword(request));
    }

    @PostMapping("/forgot-application")
    public ResponseEntity<?> forgotApplication(@RequestBody ForgotApplicationRequest request) {
        logger.info("Forgot application request for: {} {}", request.getApplicantName(), request.getFathersName());
        return ResponseEntity.ok(passwordService.forgotApplication(request));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> getForgotPasswordInfo() {
        logger.info("GET /auth/forgot-password info endpoint hit.");
        Map<String, String> response = new HashMap<>();
        response.put("info", "Use POST with application number, DOB, and captcha.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/forgot-application")
    public ResponseEntity<?> getForgotApplicationInfo() {
        logger.info("GET /auth/forgot-application info endpoint hit.");
        Map<String, String> response = new HashMap<>();
        response.put("info", "Use POST with applicant name, father's name, DOB, and captcha.");
        return ResponseEntity.ok(response);
    }
}
