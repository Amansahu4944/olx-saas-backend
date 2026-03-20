package com.olx.olx_saas.dto;

// Yahan Lombok ke annotations nahi, manual code likhna best hai
public class AuthResponse {
    
    private String token;
    private String type;

    // Constructor: Jab naya object banate hain
    public AuthResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    // Getter: Token ko bahar nikalne ke liye
    public String getToken() {
        return token;
    }

    // Setter: Token ko set karne ke liye
    public void setToken(String token) {
        this.token = token;
    }

    // Getter: Type (e.g., "Bearer") ko nikalne ke liye
    public String getType() {
        return type;
    }

    // Setter: Type ko set karne ke liye
    public void setType(String type) {
        this.type = type;
    }
}