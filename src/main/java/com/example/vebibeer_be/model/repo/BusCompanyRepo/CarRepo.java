package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.vebibeer_be.model.entities.BusCompany.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.busCompany.busCompany_id = :busCompanyId")
    List<Car> findByBusCompanyId(int busCompanyId);

}
