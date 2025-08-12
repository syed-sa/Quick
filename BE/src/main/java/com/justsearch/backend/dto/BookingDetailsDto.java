package com.justsearch.backend.dto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
@Data
@Builder
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
    public String category;
}