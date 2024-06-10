package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.DetailService;

@Repository
public interface DetailServiceRepository extends JpaRepository<DetailService, Integer> {

}
