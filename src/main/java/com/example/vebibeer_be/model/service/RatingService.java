package com.example.vebibeer_be.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.repo.RatingRepo;

@Service
public class RatingService {
    @Autowired
    RatingRepo ratingRepo;

    public List<Rating> getAll() {
        return ratingRepo.findAll();
    }

    public Rating getById(int rating_id) {
        return ratingRepo.getReferenceById(rating_id);
    }

    public void save(Rating rating) {
        ratingRepo.save(rating);
    }

    public void delete(int rating_id) {
        ratingRepo.deleteById(rating_id);
    }

    public List<Rating> getAllSortedByStars() {
        return ratingRepo.findAll().stream()
                .sorted((r1, r2) -> Integer.compare(r2.getAmount_star(), r1.getAmount_star()))
                .collect(Collectors.toList());
    }
}