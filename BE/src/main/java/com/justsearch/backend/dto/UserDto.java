package com.justsearch.backend.dto;

import java.util.Set;

public class UserDto {

    public UserDto(Long id, String name, String email, String phone, Set<RoleDto> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    private Long id;

    private String name;

    private String email;

    private String phone;

   
    private Set<RoleDto> roles;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
}