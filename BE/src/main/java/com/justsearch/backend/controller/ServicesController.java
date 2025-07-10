package com.justsearch.backend.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.justsearch.backend.dto.RegisterBusinessDto;
import com.justsearch.backend.dto.ServiceDto;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

@RestController
@RequestMapping("api/services")

public class ServicesController {

    public BuisnessRegistry _registerServicesService;

    public ServicesController(BuisnessRegistry registerServicesService) {
        this._registerServicesService = registerServicesService;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> registerService(@ModelAttribute RegisterBusinessDto service) {
        try {
            _registerServicesService.registerBusiness(service);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error registering service: " + e.getMessage());
        }
    }

    @GetMapping("/getByCategory")
    public ResponseEntity<?> getServicesByCategory(@RequestParam String categoryName, @RequestParam String postalCode) {
        try {
            List<ServiceDto> services = _registerServicesService.getServicesByCategory(categoryName, postalCode);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching services: " + e.getMessage());
        }
    }

    @GetMapping("/getImages")
    public ResponseEntity<?> getImages(@RequestParam long serviceId) {
        {
            try {
                // Assuming you have a method to fetch images from the folder path
                List<String> images = _registerServicesService.getImages(serviceId);
                return ResponseEntity.ok(images);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Error fetching images: " + e.getMessage());
            }
        }

    }

    @GetMapping("/getservice/userId/{userId}")
    public ResponseEntity<?> getServicesByUserId(@PathVariable long userId) {
        try {
            List<ServiceDto> services = _registerServicesService.getServiceByUserId(userId);
            if (services.isEmpty()) {
                return ResponseEntity.status(404).body("No services found for user ID: " + userId);
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching services: " + e.getMessage());
        }
    }

    @PutMapping("/updateService")
    public ResponseEntity<?> updateService(@RequestBody ServiceDto serviceDto) {
        try {
            _registerServicesService.updateService(serviceDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating service: " + e.getMessage());
        }
    }

    
}
