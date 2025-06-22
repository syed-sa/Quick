package com.justsearch.backend.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/services")

public class ServicesController {

    public BuisnessRegistry _registerServicesService;
    
    public ServicesController(BuisnessRegistry registerServicesService) {
        this._registerServicesService = registerServicesService;
    }


@PostMapping(value = "/register",consumes = "multipart/form-data")
    public ResponseEntity<?> registerService(@ModelAttribute RegisterServices service) {
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
        List<Services> services = _registerServicesService.getServicesByCategory(categoryName, postalCode);
        return ResponseEntity.ok(services);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error fetching services: " + e.getMessage());
    }
}

    @GetMapping("/getImages")
    public ResponseEntity<?> getImages (@RequestParam long serviceId) {
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
}


