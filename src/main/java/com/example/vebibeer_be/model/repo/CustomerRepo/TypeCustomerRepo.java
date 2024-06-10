package com.example.vebibeer_be.model.repo.CustomerRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Customer.TypeCustomer;

@Repository
public interface TypeCustomerRepo extends JpaRepository<TypeCustomer, Integer>{
    
}
