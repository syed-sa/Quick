package com.justsearch.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.justsearch.backend.dto.BookingDetailsDto;
import com.justsearch.backend.model.BookingDetails;
@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {


    @Query("SELECT new com.justsearch.backend.dto.BookingDetailsDto(" +
       "b.id, b.serviceId, b.customerId, b.serviceProviderId, " +
       "b.serviceName, b.createdAt, b.bookingStatus, u.phone, u.email, b.description,b.location) " +
       "FROM BookingDetails b JOIN User u ON b.customerId = u.id " +
       "WHERE b.serviceProviderId = :serviceProviderId")
List<BookingDetailsDto> fetchBookingsWithCustomerInfo(@Param("serviceProviderId") Long serviceProviderId);


   @Query("SELECT new com.justsearch.backend.dto.BookingDetailsDto(" +
       "b.id, b.serviceId, b.customerId, b.serviceProviderId, " +
       "b.serviceName, b.createdAt, b.bookingStatus, u.phone, u.email, b.description,b.location) " +
       "FROM BookingDetails b JOIN User u ON b.serviceProviderId = u.id " +
       "WHERE b.customerId = :customerId")
List<BookingDetailsDto> fetchBookingsWithServiceProviderInfo(@Param("customerId") Long customerId);


} 