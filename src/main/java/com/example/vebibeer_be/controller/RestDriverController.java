package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.dto.DriverDTO;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.BusCompany.Driver;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.service.BusCompanyService.DriverService;

@RestController
@RequestMapping("/buscompany/driver")
public class RestDriverController {
    @Autowired
    DriverService driverService;

    @Autowired
    BusCompanyRepo busCompanyRepo;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Driver>> showList() {
        List<Driver> Drivers = driverService.getAll();
        if (Drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Driver>>(Drivers, HttpStatus.OK);
    }

    // @GetMapping("/bycompany/{busCompanyId}")
    // public ResponseEntity<List<Driver>> showList(@PathVariable int busCompanyId) {
    //     List<Driver> drivers = driverService.getByBusCompanyId(busCompanyId);
    //     if (drivers.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(drivers, HttpStatus.OK);
    // }

    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<?> saveDriver(@RequestBody DriverDTO driverDTO) {
        if (driverDTO == null) {
            return new ResponseEntity<>("DriverDTO is null", HttpStatus.BAD_REQUEST);
        }

        try {
            Driver newDriver = new Driver(); // Convert DriverDTO to Driver entity
            newDriver.setDriver_name(driverDTO.getDriver_name());
            newDriver.setDriver_avaUrl(driverDTO.getDriver_avaUrl());
            newDriver.setDriver_description(driverDTO.getDriver_description());

            // Set BusCompany by ID
            BusCompany busCompany = busCompanyRepo.findById(driverDTO.getBusCompany_id())
                    .orElseThrow(() -> new RuntimeException("BusCompany not found"));
            newDriver.setBusCompany(busCompany);

            driverService.save(newDriver);
            return new ResponseEntity<>(newDriver, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save driver: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = { "/{id}", "/{id}/" })
    public ResponseEntity<Driver> getById(@PathVariable(name = "id") int driver_id) {
        Driver Driver = driverService.getById(driver_id);
        if (Driver == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Driver>(Driver, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<Driver> delete(@PathVariable(name = "id") int driver_id) {
        Driver Driver = driverService.getById(driver_id);
        if (Driver == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        driverService.delete(driver_id);
        return new ResponseEntity<Driver>(Driver, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable(name = "id") int id, @RequestBody Driver driverDetails) {
        try {
            Driver updatedDriver = driverService.updateDriver(id, driverDetails);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-company/{busCompanyId}")
    public ResponseEntity<List<Driver>> listDriversByBusCompany(@PathVariable int busCompanyId) {
        List<Driver> drivers = driverService.getByBusCompanyId(busCompanyId);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }
}
