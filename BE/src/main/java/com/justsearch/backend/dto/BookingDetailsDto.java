package com.justsearch.backend.dto;
import java.time.LocalDateTime;

public class BookingDetailsDto {

    public Long id;
    public Long serviceId;
    public Long customerId;
    public Long serviceProviderId;
    public String serviceName;
    public LocalDateTime createdAt;
    public String bookingStatus;
    public String phone;
    public String email;
    public String description;
    public String location;

    public BookingDetailsDto() {
    // required by ModelMapper or frameworks that use reflection
}


    public BookingDetailsDto(Long id, Long serviceId, Long customerId, Long serviceProviderId,
                         String serviceName, LocalDateTime createdAt, String bookingStatus,
                         String phone, String email, String description,String location) {
    this.id = id;
    this.serviceId = serviceId;
    this.customerId = customerId;
    this.serviceProviderId = serviceProviderId;
    this.serviceName = serviceName;
    this.createdAt = createdAt;
    this.bookingStatus = bookingStatus;
    this.phone = phone;
    this.email = email;
    this.description = description;
    this.location = location;
}
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }


    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}