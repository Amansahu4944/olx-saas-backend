package com.olx.olx_saas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // 1. Login/Signup Open
                .requestMatchers("/api/auth/**").permitAll()
                
                // 2. SWAGGER URLs (Ye Add karna zaroori hai)
                .requestMatchers(
                    "/swagger-ui/**", 
                    "/v3/api-docs/**", 
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // 3. Tenant APIs: ONLY SUPER ADMIN
                .requestMatchers("/api/tenants/**").hasAuthority("ROLE_SUPER_ADMIN")

                // 4. Product APIs
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyAuthority("ROLE_HOST", "ROLE_SUPER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/products/**").authenticated()

                // 5. Any other request
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}