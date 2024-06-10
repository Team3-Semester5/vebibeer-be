package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Rating;
import com.example.model.repo.RatingRepository;

@Service
public class RatingService {
    
    @Autowired
    private RatingRepository ratingRepository;

    // Create a new rating
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Retrieve all ratings
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // Retrieve a rating by its ID
    public Optional<Rating> getRatingById(int id) {
        return ratingRepository.findById(id);
    }

    // Update a rating
    public Rating updateRating(int id, Rating ratingDetails) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating existingRating = optionalRating.get();
            existingRating.setAmount_star(ratingDetails.getAmount_star());
            existingRating.setContent(ratingDetails.getContent());
            existingRating.setTime_to_edit(ratingDetails.getTime_to_edit());
            existingRating.setBusCompany(ratingDetails.getBusCompany());
            existingRating.setCustomer(ratingDetails.getCustomer());
            return ratingRepository.save(existingRating);
        } else {
            throw new RuntimeException("Rating not found with id " + id);
        }
    }

    // Delete a rating by its ID
    public void deleteRating(int id) {
        ratingRepository.deleteById(id);
    }
}
