package com.justsearch.backend.dto;


public class ServiceDto {
    public long id;
    public long userId;
    public String companyName;
    public String city;
    public long businessCategoryId;
    public String phone;
    public String email;
    public String website;
    public String address;
    public String postalCode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName.toUpperCase().replaceAll(" ", "_");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getBusinessCategoryId() {
        return businessCategoryId;
    }

    public void setBusinessCategoryId(long businessCategoryId) {
        this.businessCategoryId = businessCategoryId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
