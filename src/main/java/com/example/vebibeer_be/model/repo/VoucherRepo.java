package com.example.vebibeer_be.model.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Voucher;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, String> {

    @Query(value = "SELECT * FROM voucher WHERE bus_company_id = ?1 AND CURRENT_TIMESTAMP < end_time ", nativeQuery = true)
    List<Voucher> findByBusCompanyId(int busCompanyId);
}