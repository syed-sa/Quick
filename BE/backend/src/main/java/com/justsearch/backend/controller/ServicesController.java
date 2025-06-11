package com.justsearch.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justsearch.backend.dto.RegisterServices;
import com.justsearch.backend.service.RegisterServicesService;

@RestController
@RequestMapping("/api/services")

public class ServicesController {

    public RegisterServicesService _registerServicesService;
    
    public ServicesController(RegisterServicesService registerServicesService) {
        this._registerServicesService = registerServicesService;
    }

    public void registerService(RegisterServices service) {

        // Call the service layer to register the business
        _registerServicesService.registerBusiness(service);
    }


    }

