package com.example.vebibeer_be.model.service;

import com.example.vebibeer_be.model.entity.Service;
import com.example.vebibeer_be.model.repo.ServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    // Create a new Service
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    // Retrieve all Services
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Retrieve a Service by ID
    public Optional<Service> getServiceById(int id) {
        return serviceRepository.findById(id);
    }

    // Update an existing Service
    public Service updateService(int id, Service serviceDetails) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            Service existingService = optionalService.get();
            existingService.setService_name(serviceDetails.getService_name());
            existingService.setService_description(serviceDetails.getService_description());
            existingService.setService_logo(serviceDetails.getService_logo());
            return serviceRepository.save(existingService);
        } else {
            throw new RuntimeException("Service not found with id " + id);
        }
    }

    // Delete a Service by ID
    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }
}
