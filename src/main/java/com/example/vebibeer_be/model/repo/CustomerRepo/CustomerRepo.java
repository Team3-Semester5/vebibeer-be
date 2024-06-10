package com.example.vebibeer_be.model.repo.CustomerRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Customer.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    
}
