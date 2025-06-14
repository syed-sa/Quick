package com.justsearch.backend.dto;
import org.springframework.web.multipart.MultipartFile;

public class RegisterServices {

    public long userId;
    public String CompanyName;
    public String city;
    public String BusinessCategory;
    public String Phone;
    public String Email;
    public String website;
    public String Address;
    public MultipartFile[] Images;

    public RegisterServices(String companyName, String city, String buisnessCategory, String phone,
            String email, String website, String address, long userId, MultipartFile[] images) {
        CompanyName = companyName;
        this.city = city;
        this.BusinessCategory = buisnessCategory;
        this.Phone = phone;
        this.Email = email;
        this.website = website;
        this.Address = address;
        this.userId = userId;
        this.Images = images;
    }

    public String getCompanyName() {
        return CompanyName.toUpperCase().replaceAll(" ", "_");
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName.toUpperCase().replaceAll(" ", "_");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;

    }

    public String getBusinessCategory() {
        return BusinessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        BusinessCategory = businessCategory;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public MultipartFile[] getImages() {
        return Images;
    }
    public void setImages(MultipartFile[] images) {
        Images = images;
    }
}
