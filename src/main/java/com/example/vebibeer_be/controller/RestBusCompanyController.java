package com.example.vebibeer_be.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/buscompanies")
public class RestBusCompanyController {

    @Autowired
    private BusCompanyService busCompanyService;

    @PostMapping("/")
    public ResponseEntity<BusCompany> createBusCompany(@RequestBody BusCompany busCompany) {
        BusCompany createdBusCompany = busCompanyService.createBusCompany(busCompany);
        return ResponseEntity.ok(createdBusCompany);
    }

    @GetMapping("/")
    public ResponseEntity<List<BusCompany>> getAllBusCompanies() {
        List<BusCompany> busCompanies = busCompanyService.getAllBusCompanies();
        return ResponseEntity.ok(busCompanies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusCompany> getBusCompanyById(@PathVariable int id) {
        return busCompanyService.getBusCompanyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
        try {
            busCompanyService.deleteBusCompany(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}