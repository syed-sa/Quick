package com.justsearch.backend.dto;

public class TokenResponseDto {
    private long userId;
    private String userName;
    private String accessToken;
    private String refreshToken;
    private String role;

    public TokenResponseDto(String userName, String accessToken, String refreshToken, long userId, String role) {
        this.userName = userName;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;

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

    public String getUserName() {
        return userName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole()
    {
        return role;
    }

    public void SetRole(String role)
    {
        this.role = role;
    }
}
