package com.justsearch.backend.service.BusinessRegistry;

import java.util.List;

import com.justsearch.backend.dto.RegisterBusinessDto;
import com.justsearch.backend.model.Services;

public interface BuisnessRegistry {

    void registerBusiness(RegisterBusinessDto registerServices);

    List<Services> getServicesByCategory(String categoryName, String postalCode);

    List<String> getImages(long service);

}