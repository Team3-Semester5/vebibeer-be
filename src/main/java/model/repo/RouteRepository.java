package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}

