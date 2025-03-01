package org.example.mainpriject.dto;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Геттер
    public String getToken() {
        return token;
    }
}
