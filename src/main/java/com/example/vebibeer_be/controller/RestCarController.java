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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/buscompany/car")
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

    // Kiểm tra xem ID của newCar có hợp lệ không (khác 0 hoặc ID đã tồn tại)
    if (newCar.getCar_id() == 0) {
        carService.save(newCar);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    // Nếu ID không phải 0, kiểm tra xem car có tồn tại không
    Car car = carService.getById(newCar.getCar_id());
    if (car == null) {
        carService.save(newCar);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    } else {
        // Cập nhật thông tin car nếu đã tồn tại
        car.setCar_code(newCar.getCar_code());
        car.setAmount_seat(newCar.getAmount_seat());
        car.setCar_imgUrl1(newCar.getCar_imgUrl1());
        car.setCar_imgUrl2(newCar.getCar_imgUrl2());
        car.setCar_imgUrl3(newCar.getCar_imgUrl3());
        car.setCar_imgUrl4(newCar.getCar_imgUrl4());
        car.setCar_imgUrl5(newCar.getCar_imgUrl5());
        car.setCar_imgUrl6(newCar.getCar_imgUrl6());
        car.setCar_manufacturer(newCar.getCar_manufacturer());
        car.setBusCompany(newCar.getBusCompany());
        carService.save(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    
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

    @PutMapping("update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable(name = "id") int id, @RequestBody Car carDetails) {
        try {
            Car updatedCar = carService.updateCar(id, carDetails);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
