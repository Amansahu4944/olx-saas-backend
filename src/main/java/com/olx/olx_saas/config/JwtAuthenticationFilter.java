package com.olx.olx_saas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Header se Token lo
        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // "Bearer " hata ke sirf token
            email = jwtUtils.getEmailFromToken(token);
        }

        // 2. Token Validate karo aur SecurityContext set karo
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Token se Role nikalo
            String role = jwtUtils.getRoleFromToken(token);
            
            // Role ko GrantedAuthority me convert karo
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            
            // Authentication Object banao
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));
            
            // SecurityContext me set karo (Sabse Important Step)
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 3. Request aage bhejo
        filterChain.doFilter(request, response);
    }
}