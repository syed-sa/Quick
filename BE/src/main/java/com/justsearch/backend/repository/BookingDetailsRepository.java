package com.justsearch.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.justsearch.backend.model.BookingDetails;
@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {


   @Query("SELECT b FROM BookingDetails b " +
       "JOIN FETCH b.customer c " +
       "WHERE b.serviceProvider.id = :serviceProviderId")
List<BookingDetails> fetchBookingsWithCustomerInfo(@Param("serviceProviderId") Long serviceProviderId);

@Query("SELECT b FROM BookingDetails b " +
       "JOIN FETCH b.serviceProvider sp " +
       "WHERE b.customer.id = :customerId")
List<BookingDetails> fetchBookingsWithServiceProviderInfo(@Param("customerId") Long customerId);

 List<BookingDetails> findTop10ByOrderByCreatedAtDesc();

} 