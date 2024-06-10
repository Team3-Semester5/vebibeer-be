package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.DetailService;
import com.example.model.repo.DetailServiceRepository;

@Service
public class DetailServiceService {
    @Autowired
    private DetailServiceRepository detailServiceRepository;

    // Create a new DetailService
    public DetailService createDetailService(DetailService detailService) {
        return detailServiceRepository.save(detailService);
    }

    // Retrieve all DetailServices
    public List<DetailService> getAllDetailServices() {
        return detailServiceRepository.findAll();
    }

    // Retrieve a DetailService by ID
    public Optional<DetailService> getDetailServiceById(int id) {
        return detailServiceRepository.findById(id);
    }

    // Update an existing DetailService
    public DetailService updateDetailService(int id, DetailService detailServiceDetails) {
        Optional<DetailService> optionalDetailService = detailServiceRepository.findById(id);
        if (optionalDetailService.isPresent()) {
            DetailService existingDetailService = optionalDetailService.get();
            existingDetailService.setService(detailServiceDetails.getService());
            existingDetailService.setRoute(detailServiceDetails.getRoute());
            return detailServiceRepository.save(existingDetailService);
        } else {
            throw new RuntimeException("DetailService not found with id " + id);
        }
    }

    // Delete a DetailService by ID
    public void deleteDetailService(int id) {
        detailServiceRepository.deleteById(id);
    }
}