package com.olx.olx_saas.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

   
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    
    private final long jwtExpirationMs = 86400000;

   
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) 
                .claim("role", role) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) 
                .signWith(key) 
                .compact();
    }

   
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
    
    
    
    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
    
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}