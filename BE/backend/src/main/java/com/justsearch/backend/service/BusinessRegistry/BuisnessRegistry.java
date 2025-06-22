package com.justsearch.backend.service.BusinessRegistry;

import java.util.List;

import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;

public interface BuisnessRegistry {

    void registerBusiness(RegisterServices registerServices);
     List<Services> getServicesByCategory(String categoryName, String postalCode);
     List<String> getImages(long service);

} 