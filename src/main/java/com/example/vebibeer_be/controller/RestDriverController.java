package com.example.vebibeer_be.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.BusCompany.Driver;
import com.example.vebibeer_be.model.service.BusCompanyService.DriverService;

@RestController
@RequestMapping("/api/drivers")
public class RestDriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping("/")
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }

    @GetMapping("/")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable int id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        if (driver.isPresent()) {
            return ResponseEntity.ok(driver.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable int id, @RequestBody Driver driverDetails) {
        try {
            Driver updatedDriver = driverService.updateDriver(id, driverDetails);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable int id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
