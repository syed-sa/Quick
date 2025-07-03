package com.justsearch.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justsearch.backend.model.BookingDetails;
@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {

    
} 