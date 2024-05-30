package com.example.controller;

import com.example.model.entity.Service;
import com.example.model.service.ServiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceService.createService(service);
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable int id) {
        Optional<Service> service = serviceService.getServiceById(id);
        if (service.isPresent()) {
            return ResponseEntity.ok(service.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable int id, @RequestBody Service serviceDetails) {
        try {
            Service updatedService = serviceService.updateService(id, serviceDetails);
            return ResponseEntity.ok(updatedService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable int id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
