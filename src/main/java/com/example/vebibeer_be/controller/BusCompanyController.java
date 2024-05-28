package com.example.vebibeer_be.controller;

import com.example.vebibeer_be.model.entity.BusCompany;
import com.example.vebibeer_be.model.service.BusCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buscompanies")
public class BusCompanyController {

    @Autowired
    private BusCompanyService busCompanyService;

    @PostMapping("/")
    public BusCompany createBusCompany(@RequestBody BusCompany busCompany) {
        return busCompanyService.createBusCompany(busCompany);
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
    public ResponseEntity<Void> deleteBusCompany(@PathVariable(name = "id") int id) {
        busCompanyService.deleteBusCompany(id);
        return ResponseEntity.noContent().build();
    }
}