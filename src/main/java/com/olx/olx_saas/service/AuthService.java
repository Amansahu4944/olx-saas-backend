package com.olx.olx_saas.service;

import com.olx.olx_saas.config.JwtUtils; 
import com.olx.olx_saas.dto.AuthResponse;
import com.olx.olx_saas.dto.LoginRequest;
import com.olx.olx_saas.model.User;
import com.olx.olx_saas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils; 

    public AuthResponse login(LoginRequest request) {
        
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        boolean isPasswordCorrect = false;

        
        if (user.getPassword().startsWith("$2a$")) {
            isPasswordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
        } 
        
        else {
            isPasswordCorrect = request.getPassword().equals(user.getPassword());
        }

        
        if (!isPasswordCorrect) {
            throw new RuntimeException("Invalid Password");
        }

        
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, "Bearer");
    }
}