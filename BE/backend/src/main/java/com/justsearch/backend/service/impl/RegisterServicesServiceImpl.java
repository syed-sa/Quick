package com.justsearch.backend.service.impl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.RegisterServicesService;

@Service
public class RegisterServicesServiceImpl implements RegisterServicesService {
    private ServicesRepository _servicesRepository;

    @Value("${basepath}")
    private String basePath;

    public RegisterServicesServiceImpl(ServicesRepository servicesRepository) {
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
}
