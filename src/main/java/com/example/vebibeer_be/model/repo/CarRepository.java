package com.example.vebibeer_be.model.repo;

import com.example.vebibeer_be.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    // Additional query methods (if any) can be defined here
}
