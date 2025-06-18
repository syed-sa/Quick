package com.justsearch.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.service.BusinessRegistry.BuisnessRegistry;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



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
}

