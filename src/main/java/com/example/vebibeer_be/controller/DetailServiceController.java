package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.model.entity.DetailService;
import com.example.model.service.DetailServiceService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/detailservices")
public class DetailServiceController {

    @Autowired
    private DetailServiceService detailServiceService;

    @PostMapping
    public DetailService createDetailService(@RequestBody DetailService detailService) {
        return detailServiceService.createDetailService(detailService);
    }

    @GetMapping
    public List<DetailService> getAllDetailServices() {
        return detailServiceService.getAllDetailServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailService> getDetailServiceById(@PathVariable int id) {
        Optional<DetailService> detailService = detailServiceService.getDetailServiceById(id);
        if (detailService.isPresent()) {
            return ResponseEntity.ok(detailService.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailService> updateDetailService(@PathVariable int id,
            @RequestBody DetailService detailServiceDetails) {
        try {
            DetailService updatedDetailService = detailServiceService.updateDetailService(id, detailServiceDetails);
            return ResponseEntity.ok(updatedDetailService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailService(@PathVariable int id) {
        detailServiceService.deleteDetailService(id);
        return ResponseEntity.noContent().build();
    }
}

