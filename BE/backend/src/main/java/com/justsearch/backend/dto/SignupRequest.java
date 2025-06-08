package com.justsearch.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 6)
    private String password;


    // Manual Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Manual Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
