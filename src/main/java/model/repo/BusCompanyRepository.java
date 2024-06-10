package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.BusCompany;

@Repository
public interface BusCompanyRepository extends JpaRepository<BusCompany, Integer> {

}
