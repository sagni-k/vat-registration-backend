package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.LoginRequest;
import com.nic.vat.registration.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.service.PasswordService;
import com.nic.vat.registration.model.dto.ForgotApplicationRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(passwordService.forgotPassword(request));
    }

    @PostMapping("/forgot-application")
    public ResponseEntity<?> forgotApplication(@RequestBody ForgotApplicationRequest request) {
        return ResponseEntity.ok(passwordService.forgotApplication(request));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> getForgotPasswordInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("info", "Use POST with application number, DOB, and captcha.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/forgot-application")
    public ResponseEntity<?> getForgotApplicationInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("info", "Use POST with applicant name, father's name, DOB, and captcha.");
        return ResponseEntity.ok(response);
    }


}

