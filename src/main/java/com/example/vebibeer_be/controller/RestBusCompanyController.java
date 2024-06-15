package com.example.vebibeer_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;

import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/buscompanies")
public class RestBusCompanyController {
    @Autowired
    private BusCompanyService busCompanyService;

    @PostMapping("/")
    public ResponseEntity<BusCompany> createBusCompany(@RequestBody BusCompany busCompany) {
        try {
            BusCompany createdBusCompany = busCompanyService.createBusCompany(busCompany);
            return ResponseEntity.ok(createdBusCompany);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public List<BusCompany> getAllBusCompanies() {
        return busCompanyService.getAllBusCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusCompany> getBusCompanyById(@PathVariable int id) {
        Optional<BusCompany> busCompany = busCompanyService.getBusCompanyById(id);
        if (busCompany.isPresent()) {
            return ResponseEntity.ok(busCompany.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusCompany> updateBusCompany(@PathVariable int id, @RequestBody BusCompany busCompanyDetails) {
        try {
            BusCompany updatedBusCompany = busCompanyService.updateBusCompany(id, busCompanyDetails);
            return ResponseEntity.ok(updatedBusCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusCompany(@PathVariable int id) {
        busCompanyService.deleteBusCompany(id);
        return ResponseEntity.noContent().build();
    }
}