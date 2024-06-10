package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.CarRepo;



@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;

    // Create a new Car
    public Car createCar(Car car) {
        return carRepo.save(car);
    }

    // Retrieve all Cars
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    // Retrieve a Car by ID
    public Optional<Car> getCarById(int id) {
        return carRepo.findById(id);
    }

    // Update an existing Car
    public Car updateCar(int id, Car carDetails) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setCar_code(carDetails.getCar_code());
            existingCar.setAmount_seat(carDetails.getAmount_seat());
            existingCar.setCar_imgUrl1(carDetails.getCar_imgUrl1());
            existingCar.setCar_imgUrl2(carDetails.getCar_imgUrl2());
            existingCar.setCar_imgUrl3(carDetails.getCar_imgUrl3());
            existingCar.setCar_imgUrl4(carDetails.getCar_imgUrl4());
            existingCar.setCar_imgUrl5(carDetails.getCar_imgUrl5());
            existingCar.setCar_imgUrl6(carDetails.getCar_imgUrl6());
            existingCar.setCar_manufacturer(carDetails.getCar_manufacturer());
            existingCar.setBusCompany(carDetails.getBusCompany());
            return carRepo.save(existingCar);
        } else {
            throw new RuntimeException("Car not found with id " + id);
        }
    }

    // Delete a Car by ID
    public void deleteCar(int id) {
        carRepo.deleteById(id);
    }
}
