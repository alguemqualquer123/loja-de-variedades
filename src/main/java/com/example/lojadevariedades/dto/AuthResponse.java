package com.example.lojadevariedades.dto;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;

    public AuthResponse() {}
    public AuthResponse(String token, String refreshToken) { this.token = token; this.refreshToken = refreshToken; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
