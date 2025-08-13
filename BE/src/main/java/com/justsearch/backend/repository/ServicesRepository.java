package com.justsearch.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justsearch.backend.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    // Checks if a service exists by service provider (user) ID and company name
    boolean existsByServiceProviderIdAndCompanyName(long serviceProviderId, String companyName);

    // Find services by business category ID and postal code
    List<Services> findByBusinessCategoryIdAndPostalCode(long businessCategoryId, String postalCode);

    // Find all services by service provider (user) ID
    List<Services> findAllByServiceProviderId(long serviceProviderId);
}
