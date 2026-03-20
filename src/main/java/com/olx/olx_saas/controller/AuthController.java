package com.olx.olx_saas.controller;

import com.olx.olx_saas.dto.AuthResponse;
import com.olx.olx_saas.dto.LoginRequest;
import com.olx.olx_saas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}