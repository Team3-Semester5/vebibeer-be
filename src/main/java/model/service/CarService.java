package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Car;
import com.example.model.repo.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Create a new Car
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    // Retrieve all Cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Retrieve a Car by ID
    public Optional<Car> getCarById(int id) {
        return carRepository.findById(id);
    }

    // Update an existing Car
    public Car updateCar(int id, Car carDetails) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setCar_code(carDetails.getCar_code());
            existingCar.setCar_amount_seat(carDetails.getCar_amount_seat());
            existingCar.setCar_img1(carDetails.getCar_img1());
            existingCar.setCar_img2(carDetails.getCar_img2());
            existingCar.setCar_img3(carDetails.getCar_img3());
            existingCar.setCar_img4(carDetails.getCar_img4());
            existingCar.setCar_img5(carDetails.getCar_img5());
            existingCar.setCar_img6(carDetails.getCar_img6());
            existingCar.setCar_manufacturer(carDetails.getCar_manufacturer());
            existingCar.setBusCompany(carDetails.getBusCompany());
            return carRepository.save(existingCar);
        } else {
            throw new RuntimeException("Car not found with id " + id);
        }
    }

    // Delete a Car by ID
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}

