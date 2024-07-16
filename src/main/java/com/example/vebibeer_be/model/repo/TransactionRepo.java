package com.example.vebibeer_be.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.Customer.Customer;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomer(Customer customer);

}
