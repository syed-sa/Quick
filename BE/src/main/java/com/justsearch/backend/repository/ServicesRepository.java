package com.justsearch.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.justsearch.backend.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    // Checks if a service exists by service provider (user) ID and company name
    boolean existsByServiceProviderIdAndCompanyName(long serviceProviderId, String companyName);

   @Query("SELECT s FROM Services s WHERE s.businessCategory.id = :categoryId AND s.postalCode = :postalCode")
List<Services> findByCategoryAndPostal(@Param("categoryId") Long categoryId, @Param("postalCode") String postalCode);


    // Find all services by service provider (user) ID
    List<Services> findAllByServiceProviderId(long serviceProviderId);
}
