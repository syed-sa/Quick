package com.justsearch.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justsearch.backend.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    // Custom query methods (if needed) can be defined here

    boolean existsByUserIdAndCompanyName(long userId, String companyName);

    List<Services> findByBusinessCategoryIdAndPostalCode(long businessCategoryId, String postalCode);
    
}
