package com.justsearch.backend.service.impl;

import org.springframework.stereotype.Service;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.RegisterServicesService;
@Service
public class RegisterServicesServiceImpl implements RegisterServicesService {
    private UserRepository _userRepository;
    private ServicesRepository _servicesRepository;

    public RegisterServicesServiceImpl(UserRepository userRepository, ServicesRepository servicesRepository) {
        _userRepository = userRepository;
        _servicesRepository = servicesRepository;
    }

    public void registerBusiness(RegisterServices registerServices) {
        if (_userRepository.existsById(registerServices.getUserId())) {
            // User exists, proceed with business registration
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
        } else {
            // User does not exist, handle accordingly
            throw new IllegalArgumentException("User with ID " + registerServices.getUserId() + " does not exist.");
        }
    }
}
