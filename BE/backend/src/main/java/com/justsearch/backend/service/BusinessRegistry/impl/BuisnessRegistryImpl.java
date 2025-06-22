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
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.repository.CategoryRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

@Service
public class BuisnessRegistryImpl implements BuisnessRegistry {
    private ServicesRepository _servicesRepository;
    private CategoryRepository _categoryRepository;

    @Value("${basepath}")
    private String basePath;

    public BuisnessRegistryImpl(ServicesRepository servicesRepository, CategoryRepository categoryRepository) {
        _servicesRepository = servicesRepository;
        _categoryRepository = categoryRepository;
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
            services.setBusinessCategoryId(registerServices.getBusinessCategoryId());
            services.setAddress(registerServices.getAddress());
            String folderPath = basePath + AppConstants.USER_DATA +AppConstants.IMAGE_FOLDER + registerServices.getUserId();
            int counter = registerServices.getImages().length;
            for (MultipartFile image : registerServices.getImages()) {
                String fileName = String.format(AppConstants.IMAGE_TEMPLATE, counter);
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

    public List<Services> getServicesByCategory(String categoryName, String postalCode) {
        if (categoryName == null || postalCode == null) {
            throw new IllegalArgumentException("Category name and postal code must not be null");
        }
        String businessCategoryName = categoryName.replace(" ","_").toUpperCase();
        var businessCategory = _categoryRepository.findByName(businessCategoryName);
        if (businessCategory == null) {
            throw new IllegalArgumentException("Category not found: " + businessCategoryName);
        }
        Long categoryId = businessCategory.getId();
        List<Services> services = _servicesRepository.findByBusinessCategoryIdAndPostalCode(categoryId, postalCode);
        return services;
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
                    .map(file -> baseUrl + "/images/" +serviceId + "/"+ file.getFileName().toString())
                    .collect(Collectors.toList());

            return imageUrls;

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve images for service ID: " + serviceId, e);
        }

    }
}