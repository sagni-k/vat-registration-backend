package com.nic.vat.registration.controller;

import com.nic.vat.registration.model.dto.LoginRequest;
import com.nic.vat.registration.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nic.vat.registration.model.dto.ForgotPasswordRequest;
import com.nic.vat.registration.service.PasswordService;
import com.nic.vat.registration.model.dto.ForgotApplicationRequest;

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

}

