package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.BusCompany.Location;



@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

}
