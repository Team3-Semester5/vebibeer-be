package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.Rating;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}

