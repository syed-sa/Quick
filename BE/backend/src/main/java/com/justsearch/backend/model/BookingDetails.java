package com.justsearch.backend.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;
    public Long customerId;
    public Long serviceproviderId;
    public LocalDate Date;
    public LocalTime startTime;
    public LocalTime endTime;
    public String bookingstatus; 
}
