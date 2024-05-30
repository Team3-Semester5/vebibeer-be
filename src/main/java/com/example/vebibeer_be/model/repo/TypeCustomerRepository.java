package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.TypeCustomer;
@Repository
public interface TypeCustomerRepository extends JpaRepository<TypeCustomer, Integer> {

}