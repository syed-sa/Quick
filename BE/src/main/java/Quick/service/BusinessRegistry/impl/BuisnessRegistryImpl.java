package Quick.service.BusinessRegistry.impl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import Quick.constants.AppConstants;
import Quick.dto.RegisterBusinessDto;
import Quick.dto.ServiceDto;
import Quick.model.Services;
import Quick.repository.CategoryRepository;
import Quick.repository.ServicesRepository;
import Quick.service.BusinessRegistry.BuisnessRegistry;

@Service
public class BuisnessRegistryImpl implements BuisnessRegistry {
    private ServicesRepository _servicesRepository;
    private CategoryRepository _categoryRepository;
    private final ModelMapper serviceMapper;
    @Value("${basepath}")
    private String basePath;

    public BuisnessRegistryImpl(ServicesRepository servicesRepository, CategoryRepository categoryRepository,
            ModelMapper serviceMapper) {
        _servicesRepository = servicesRepository;
        _categoryRepository = categoryRepository;
        this.serviceMapper = serviceMapper;
        _categoryRepository = categoryRepository;
    }

    public void registerBusiness(RegisterBusinessDto registerServices) {
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
            String folderPath = basePath + AppConstants.USER_DATA + AppConstants.IMAGE_FOLDER
                    + registerServices.getUserId();
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
        List<ServiceDto> serviceDtos = services.stream()
                .map(source -> serviceMapper.map(source, ServiceDto.class))
            .collect(Collectors.toList());
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
        List<Services> services = _servicesRepository.findAllByUserId(userId);
        List<ServiceDto> serviceDtos = services.stream()
                .map(source -> serviceMapper.map(source, ServiceDto.class))
                .collect(Collectors.toList());
        return serviceDtos;
    }

    public void updateService(ServiceDto service) {
        if (service == null || service.getId() == 0) {
            throw new IllegalArgumentException("Service and Service ID must not be null");
        }
        if (!_servicesRepository.existsById(service.getId())) {
            throw new IllegalArgumentException("Service with ID " + service.getId() + " does not exist");
        }
        _servicesRepository.save(serviceMapper.map(service, Services.class));
    }

}
