package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.CarRepo;

@Service
public class CarService {
    @Autowired
    CarRepo carRepo;

    public List<Car> getAll(){
        return carRepo.findAll();
    }

    public Car getById(int carId){
        return carRepo.getReferenceById(carId);
    }

    public void save(Car car){
        carRepo.save(car);
    }

    public void delete(int carId){
        carRepo.deleteById(carId);
    }

}
