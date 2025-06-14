package com.justsearch.backend.service.impl;
import org.springframework.stereotype.Service;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.RegisterServicesService;
@Service
public class RegisterServicesServiceImpl implements RegisterServicesService {
    private ServicesRepository _servicesRepository;

    public RegisterServicesServiceImpl(ServicesRepository servicesRepository) {
        _servicesRepository = servicesRepository;
    }

    public void registerBusiness(RegisterServices registerServices) {
        if (_servicesRepository.existsByUserIdAndCompanyName(registerServices.getUserId(), registerServices.getCompanyName())) {
            throw new IllegalStateException("Business has already been registered by this user.");
        } else {
            // Proceed with business registration
            Services services = new Services();
            services.setUserId(registerServices.getUserId());
            services.setCompanyName(registerServices.getCompanyName());
            services.setCity(registerServices.getCity());
            services.setBusinessCategory(registerServices.getBusinessCategory());
            services.setPhone(registerServices.getPhone());
            services.setEmail(registerServices.getEmail());
            services.setWebsite(registerServices.getWebsite());
            services.setAddress(registerServices.getAddress());

            _servicesRepository.save(services);
        }
    }
}
