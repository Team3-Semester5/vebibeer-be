package com.example.vebibeer_be.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Voucher;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, String> {
    
}