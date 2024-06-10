package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Driver;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.DriverRepo;




@Service
public class DriverService {
    @Autowired
    private DriverRepo driverRepo;

    // Create a new Driver
    public Driver createDriver(Driver driver) {
        return driverRepo.save(driver);
    }

    // Retrieve all Drivers
    public List<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    // Retrieve a Driver by ID
    public Optional<Driver> getDriverById(int id) {
        return driverRepo.findById(id);
    }

    // Update an existing Driver
    public Driver updateDriver(int id, Driver driverDetails) {
        Optional<Driver> optionalDriver = driverRepo.findById(id);
        if (optionalDriver.isPresent()) {
            Driver existingDriver = optionalDriver.get();
            existingDriver.setDriver_name(driverDetails.getDriver_name());
            existingDriver.setDriver_avaUrl(driverDetails.getDriver_avaUrl());
            existingDriver.setDriver_description(driverDetails.getDriver_description());
            return driverRepo.save(existingDriver);
        } else {
            throw new RuntimeException("Driver not found with id " + id);
        }
    }

    // Delete a Driver by ID
    public void deleteDriver(int id) {
        driverRepo.deleteById(id);
    }
}
