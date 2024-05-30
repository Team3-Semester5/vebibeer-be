package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Driver;
import com.example.model.repo.DriverRepository;


@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    // Create a new Driver
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Retrieve all Drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Retrieve a Driver by ID
    public Optional<Driver> getDriverById(int id) {
        return driverRepository.findById(id);
    }

    // Update an existing Driver
    public Driver updateDriver(int id, Driver driverDetails) {
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        if (optionalDriver.isPresent()) {
            Driver existingDriver = optionalDriver.get();
            existingDriver.setDriver_name(driverDetails.getDriver_name());
            existingDriver.setDriver_ava(driverDetails.getDriver_ava());
            existingDriver.setDriver_description(driverDetails.getDriver_description());
            return driverRepository.save(existingDriver);
        } else {
            throw new RuntimeException("Driver not found with id " + id);
        }
    }

    // Delete a Driver by ID
    public void deleteDriver(int id) {
        driverRepository.deleteById(id);
    }
}

