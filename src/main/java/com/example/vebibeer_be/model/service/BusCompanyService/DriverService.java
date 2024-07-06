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
    DriverRepo driverRepo;

    public List<Driver> getAll(){
        return driverRepo.findAll();
    }

    public Driver getById(int driver_id){
        return driverRepo.getReferenceById(driver_id);
    }

    public void save(Driver driver){
        driverRepo.save(driver);
    }

    public void delete(int driver_id){
        driverRepo.deleteById(driver_id);
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

}
