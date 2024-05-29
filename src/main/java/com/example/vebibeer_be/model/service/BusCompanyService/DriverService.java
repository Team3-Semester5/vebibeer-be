package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

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

}
