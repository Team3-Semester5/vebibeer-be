package com.example.vebibeer_be.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.repo.RatingRepo;

@Service
public class RatingService {
    @Autowired
    RatingRepo ratingRepo;

    public List<Rating> getAll(){
        return ratingRepo.findAll();
    }

    public Rating getById(int rating_id){
        return ratingRepo.getReferenceById(rating_id);
    }

    public void save(Rating rating){
        ratingRepo.save(rating);
    }

    public void delete(int rating_id){
        ratingRepo.deleteById(rating_id);
    }

}
