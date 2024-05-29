package com.example.vebibeer_be.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.service.BusCompanyService.CarService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/car")
public class RestCarController {
    @Autowired
    CarService carService = new CarService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Car>> showList() {
        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Car> save(@RequestBody Car newCar) {
        if (newCar == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Car car = carService.getById(newCar.getCar_id());
        if (car == null) {
            carService.save(car);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Car> getById(@PathVariable(name = "id")int car_id) {
        Car car = carService.getById(car_id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Car> delete(@PathVariable(name = "id") int car_id){
        Car car = carService.getById(car_id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        carService.delete(car_id);
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

}
