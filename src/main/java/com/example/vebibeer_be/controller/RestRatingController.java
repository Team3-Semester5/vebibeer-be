package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RestRatingController {
    @Autowired
    RatingService ratingService = new RatingService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Rating>> showList() {
        List<Rating> Ratings = ratingService.getAll();
        if (Ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Rating>>(Ratings, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Rating> save(@RequestBody Rating newRating) {
        if (newRating == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Rating Rating = ratingService.getById(newRating.getRating_id());
        if (Rating == null) {
            ratingService.save(Rating);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        ratingService.save(Rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{idBus}", "/{idBus}/"})
    public ResponseEntity<List<Rating>> getByIdBus(@PathVariable(name = "idBus")int idBus) {
        List<Rating> ratings = new ArrayList<>();
        for (Rating rating : ratingService.getAll()) {
            if (rating.getBusCompany().getBusCompany_id() == idBus) {
                ratings.add(rating);
            }
        }
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Rating> delete(@PathVariable(name = "id") int rating_id){
        Rating Rating = ratingService.getById(rating_id);
        if (Rating == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ratingService.delete(rating_id);
        return new ResponseEntity<Rating>(Rating, HttpStatus.OK);
    }

}
