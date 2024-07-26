package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.BusCompany.Driver;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {
    @Query("SELECT d FROM Driver d WHERE d.busCompany.busCompany_id = :busCompanyId")
    List<Driver> findByBusCompanyId(int busCompanyId);

}
