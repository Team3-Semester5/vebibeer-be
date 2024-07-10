
package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.CarRepo;

import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepo carRepo;

    @Autowired
    BusCompanyRepo busCompanyRepo;

    public List<Car> getAll() {
        return carRepo.findAll();
    }

    public Car getById(int carId) {
        return carRepo.getReferenceById(carId);
    }

    public void save(Car car) {
        carRepo.save(car);
    }

    public void delete(int carId) {
        carRepo.deleteById(carId);
    }

    // Update an existing Car
    public Car updateCar(int id, Car carDetails) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if (optionalCar != null) {
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

    public List<Car> getCarsByBusCompanyId(int busCompanyId) {
        return carRepo.findByBusCompanyId(busCompanyId);
    }
}
