package com.justsearch.backend.dto;

public class TokenResponse {
    private long userId;
    private String accessToken;
    
    private String refreshToken;

    public TokenResponse(String accessToken, String refreshToken, long userId) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
