package com.example.vebibeer_be.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.dto.CarDTO;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.service.BusCompanyService.CarService;

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

@RestController
@RequestMapping(value = "/buscompany/car")
public class RestCarController {
    @Autowired
    CarService carService = new CarService();

    @Autowired
    BusCompanyRepo busCompanyRepo;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Car>> showList() {
        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    // @GetMapping("/by-company/{busCompanyId}")
    // public ResponseEntity<List<Car>> showList(@PathVariable int busCompanyId) {
    //     List<Car> cars = carService.getCarsByBusCompanyId(busCompanyId);
    //     if (cars.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(cars, HttpStatus.OK);
    // }

    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<?> save(@RequestBody CarDTO carDTO) {
        if (carDTO == null) {
            return new ResponseEntity<>("CarDTO is null", HttpStatus.BAD_REQUEST);
        }

        try {
            Car newCar = new Car(); // Convert CarDTO to Car entity
            newCar.setCar_code(carDTO.getCar_code());
            newCar.setAmount_seat(carDTO.getAmount_seat());
            newCar.setCar_imgUrl1(carDTO.getCar_imgUrl1());
            newCar.setCar_imgUrl2(carDTO.getCar_imgUrl2());
            newCar.setCar_imgUrl3(carDTO.getCar_imgUrl3());
            newCar.setCar_imgUrl4(carDTO.getCar_imgUrl4());
            newCar.setCar_imgUrl5(carDTO.getCar_imgUrl5());
            newCar.setCar_imgUrl6(carDTO.getCar_imgUrl6());
            newCar.setCar_manufacturer(carDTO.getCar_manufacturer());

            // Set BusCompany by ID
            BusCompany busCompany = busCompanyRepo.findById(carDTO.getBusCompany_id())
                    .orElseThrow(() -> new RuntimeException("BusCompany not found"));
            newCar.setBusCompany(busCompany);

            carService.save(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception
            return new ResponseEntity<>("Failed to save car: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<Car> delete(@PathVariable(name = "id") int car_id) {
        Car car = carService.getById(car_id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        carService.delete(car_id);
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable(name = "id") int id, @RequestBody Car carDetails) {
        try {
            Car updatedCar = carService.updateCar(id, carDetails);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-company/{busCompanyId}")
    public ResponseEntity<List<Car>> getCarsByBusCompany(@PathVariable int busCompanyId) {
        List<Car> cars = carService.getCarsByBusCompanyId(busCompanyId);
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}