package com.example.vebibeer_be.model.service.BusCompanyService;

import com.example.vebibeer_be.model.entities.BusCompany.Service;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.vebibeer_be.model.repo.BusCompanyRepo.ServiceRepo;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepo serviceRepo;

    // Create a new Service
    public Service createService(Service service) {
        return serviceRepo.save(service);
    }

    // Retrieve all Services
    public List<Service> getAllServices() {
        return serviceRepo.findAll();
    }

    // Retrieve a Service by ID
    public Optional<Service> getServiceById(int id) {
        return serviceRepo.findById(id);
    }

    // Update an existing Service
    public Service updateService(int id, Service serviceDetails) {
        return serviceRepo.findById(id).map(existingService -> {
            existingService.setService_name(serviceDetails.getService_name());
            existingService.setService_logoUrl(serviceDetails.getService_logoUrl());
            existingService.setService_description(serviceDetails.getService_description());
            return serviceRepo.save(existingService);
        }).orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }

    // Delete a Service by ID
    public void deleteService(int id) {
        if (!serviceRepo.existsById(id)) {
            throw new RuntimeException("Service not found with id " + id);
        }
        serviceRepo.deleteById(id);
    }
}