package com.justsearch.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.service.RegisterServicesService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/services")

public class ServicesController {

    public RegisterServicesService _registerServicesService;
    
    public ServicesController(RegisterServicesService registerServicesService) {
        this._registerServicesService = registerServicesService;
    }


@PostMapping("/register")
    public ResponseEntity<?> registerService(@ModelAttribute RegisterServices service) {
   try {
       _registerServicesService.registerBusiness(service);
       return ResponseEntity.ok().build();
   } catch (Exception e) {
       return ResponseEntity.internalServerError().body("Error registering service: " + e.getMessage());
   }
}
}

