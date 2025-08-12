package com.justsearch.backend.service.BusinessRegistry.impl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.RegisterBusinessDto;  
import com.justsearch.backend.dto.ServiceDto;
import com.justsearch.backend.mapper.ServiceMapper;
import com.justsearch.backend.model.BuisnessCategory;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.CategoryRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

@Service
public class BuisnessRegistryImpl implements BuisnessRegistry {
    private ServicesRepository _servicesRepository;
    private CategoryRepository _categoryRepository;
    private UserRepository _userRepository;
    private final ServiceMapper serviceMapper;
    @Value("${basepath}")
    private String basePath;

    public BuisnessRegistryImpl(ServicesRepository servicesRepository, CategoryRepository categoryRepository,
            UserRepository userRepository, ServiceMapper serviceMapper) {
        _servicesRepository = servicesRepository;
        _categoryRepository = categoryRepository;
        _userRepository = userRepository;
        this.serviceMapper = serviceMapper;
    }

    public void registerBusiness(RegisterBusinessDto registerServices) {
        try{
        if (_servicesRepository.existsByServiceProviderIdAndCompanyName(registerServices.getUserId(),
                registerServices.getCompanyName())) {
            throw new IllegalStateException("Business has already been registered by this user.");
        } else {
            Services services = new Services();
            User serviceProvider = _userRepository.findById(registerServices.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            BuisnessCategory businessCategory = _categoryRepository.findById(registerServices.getBusinessCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Business category not found"));
            services.setServiceProvider(serviceProvider);
            services.setCompanyName(registerServices.getCompanyName());
            services.setCity(registerServices.getCity());
            services.setBusinessCategory(businessCategory);
            services.setAddress(registerServices.getAddress());
            services.setEmail(registerServices.getEmail());
            services.setPhone(registerServices.getPhone());
            String folderPath = basePath + AppConstants.USER_DATA + AppConstants.IMAGE_FOLDER
                    + registerServices.getUserId();
            if (registerServices.getImages() != null && registerServices.getImages().length > 0) {
            int counter = registerServices.getImages().length;
            for (MultipartFile image : registerServices.getImages()) {
                String fileName = String.format(AppConstants.IMAGE_TEMPLATE, counter);
                Path filePath = Path.of(folderPath, fileName);
                counter--;
                Files.createDirectories(filePath.getParent());
                    Files.copy(image.getInputStream(), filePath);
            }
               
            }
            _servicesRepository.save(services);
        }
    }
         catch (Exception e) {
                    throw new RuntimeException("Failed to save business: " +  e);
                }
    }

    public List<ServiceDto> getServicesByCategory(String categoryName, String postalCode) {
        if (categoryName == null || postalCode == null) {
            throw new IllegalArgumentException("Category name and postal code must not be null");
        }
        String businessCategoryName = categoryName.replace(" ", "_").toUpperCase();
        var businessCategory = _categoryRepository.findByName(businessCategoryName);
        if (businessCategory == null) {
            throw new IllegalArgumentException("Category not found: " + businessCategoryName);
        }
        Long categoryId = businessCategory.getId();
        List<Services> services = _servicesRepository.findByBusinessCategoryIdAndPostalCode(categoryId, postalCode);
        List<ServiceDto> serviceDtos = serviceMapper.toDtoList(services);
        return serviceDtos;
    }

    public List<String> getImages(long serviceId) {
        try {
            Path path = Path.of(basePath, AppConstants.USER_DATA, AppConstants.IMAGE_FOLDER, String.valueOf(serviceId));
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Folder does not exist for service ID: " + serviceId);
            }
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            List<Path> files = Files.list(path)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            List<String> imageUrls = files.stream()
                    .map(file -> baseUrl + "/images/" + serviceId + "/" + file.getFileName().toString())
                    .collect(Collectors.toList());

            return imageUrls;

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve images for service ID: " + serviceId, e);
        }
    }

    public List<ServiceDto> getServiceByUserId(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero");
        }
        List<Services> services = _servicesRepository.findAllByServiceProviderId(userId);
        List<ServiceDto> serviceDtos = serviceMapper.toDtoList(services);
        return serviceDtos;
    }

    public void updateService(ServiceDto service) {
        if (service == null || service.getId() == 0) {
            throw new IllegalArgumentException("Service and Service ID must not be null");
        }
        if (!_servicesRepository.existsById(service.getId())) {
            throw new IllegalArgumentException("Service with ID " + service.getId() + " does not exist");
        }
        _servicesRepository.save(serviceMapper.toEntity(service));
    }

}
