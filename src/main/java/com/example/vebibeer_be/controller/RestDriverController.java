package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.BusCompany.Driver;
import com.example.vebibeer_be.model.service.BusCompanyService.DriverService;


@RestController
@RequestMapping("/buscomapany/driver")
public class RestDriverController {
     @Autowired
    DriverService driverService = new DriverService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Driver>> showList() {
        List<Driver> Drivers = driverService.getAll();
        if (Drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Driver>>(Drivers, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Driver> save(@RequestBody Driver newDriver) {
        if (newDriver == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Driver Driver = driverService.getById(newDriver.getDriver_id());
        if (Driver == null) {
            driverService.save(Driver);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        driverService.save(Driver);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Driver> getById(@PathVariable(name = "id")int driver_id) {
        Driver Driver = driverService.getById(driver_id);
        if (Driver == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Driver>(Driver, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Driver> delete(@PathVariable(name = "id") int driver_id){
        Driver Driver = driverService.getById(driver_id);
        if (Driver == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        driverService.delete(driver_id);
        return new ResponseEntity<Driver>(Driver, HttpStatus.OK);
    }

}
