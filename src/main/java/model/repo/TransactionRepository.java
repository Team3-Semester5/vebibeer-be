package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}