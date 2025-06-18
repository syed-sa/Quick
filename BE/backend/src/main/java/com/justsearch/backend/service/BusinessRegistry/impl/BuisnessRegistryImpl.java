package com.justsearch.backend.service.BusinessRegistry.impl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

@Service
public class BuisnessRegistryImpl implements BuisnessRegistry {
    private ServicesRepository _servicesRepository;

    @Value("${basepath}")
    private String basePath;

    public BuisnessRegistryImpl(ServicesRepository servicesRepository) {
        _servicesRepository = servicesRepository;
    }

    public void registerBusiness(RegisterServices registerServices) {
        if (_servicesRepository.existsByUserIdAndCompanyName(registerServices.getUserId(),
                registerServices.getCompanyName())) {
            throw new IllegalStateException("Business has already been registered by this user.");
        } else {
            Services services = new Services();
            services.setUserId(registerServices.getUserId());
            services.setCompanyName(registerServices.getCompanyName());
            services.setCity(registerServices.getCity());
            services.setBusinessCategory(registerServices.getBusinessCategory());
            services.setAddress(registerServices.getAddress());
            String folderPath = basePath + AppConstants.USER_DATA + registerServices.getUserId();
            services.setFolderPath(folderPath);
            int counter = registerServices.getImages().length;
            for (MultipartFile image : registerServices.getImages()) {
                String fileName =  String.format(AppConstants.IMAGE_TEMPLATE, counter);
                Path filePath = Path.of(folderPath, fileName);
                counter--;
                try {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(image.getInputStream(), filePath);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to save image: " + fileName, e);
                }
            }
             _servicesRepository.save(services);
        }
    }

        public List<RegisterServices> getServicesByCategory(String category, String postalCode) {
        if (category == null || postalCode == null) {
            throw new IllegalArgumentException("Category and postal code must not be null");
        }
        var services = _servicesRepository.findByBusinessCategoryAndPostalCode(category, postalCode);
        return services.stream().map(this::convertToDto).toList();
    }

    private RegisterServices convertToDto(Services services) {
        RegisterServices dto = new RegisterServices();
        dto.setUserId(services.getUserId());
        dto.setCompanyName(services.getCompanyName());
        dto.setCity(services.getCity());
        dto.setBusinessCategory(services.getBusinessCategory());
        dto.setAddress(services.getAddress());
        // Set other fields as needed
        return dto;
    }
}
