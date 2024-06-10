package com.example.vebibeer_be.model.repo.CustomerRepo;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByUsernameAndVerificationCode(String username, String verificationCode);
}