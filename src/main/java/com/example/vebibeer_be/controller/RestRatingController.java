package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.vebibeer_be.dto.RatingDTO;
import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.RatingService;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;

@RestController
@RequestMapping("/rating")
public class RestRatingController {
    @Autowired
    RatingService ratingService = new RatingService();
    @Autowired
    CustomerService customerService = new CustomerService();
    @Autowired
    BusCompanyService busCompanyService = new BusCompanyService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Rating>> showList() {
        List<Rating> Ratings = ratingService.getAll();
        if (Ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Rating>>(Ratings, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Rating> save(@RequestBody RatingDTO newRating) {
        if (newRating == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(newRating.toString());
        int customerId = newRating.getCustomer_id();
        System.out.println(customerId);
        Customer customer = customerService.getCustomerById(customerId);
        Rating rating = new Rating(0, newRating.getAmount_star(), newRating.getRating_content(), newRating.getRating_editTime(), customer, busCompanyService.getById(newRating.getBusCompany_id()));
        ratingService.save(rating);
        return ResponseEntity.ok(rating);
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
