package com.justsearch.backend.service.BusinessRegistry;
import java.util.List;
import com.justsearch.backend.dto.RegisterBusinessDto;
import com.justsearch.backend.dto.ServiceDto;

public interface BuisnessRegistry {

    void registerBusiness(RegisterBusinessDto registerServices);

    List<ServiceDto> getServicesByCategory(String categoryName, String postalCode);

    List<String> getImages(long service);

    List<ServiceDto> getServiceByUserId(Long userId);

    void updateService(ServiceDto service);

}