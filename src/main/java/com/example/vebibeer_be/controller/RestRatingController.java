package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.dto.RatingDTO;
import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
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

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Rating>> showList() {
        List<Rating> Ratings = ratingService.getAll();
        if (Ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Rating>>(Ratings, HttpStatus.OK);
    }

    // new
    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<Map<String, String>> save(@RequestBody RatingDTO newRating) {

        Map<String, String> response = new HashMap<>();

        Customer customer = customerService.findByUsername(newRating.getUsername()).orElse(new Customer());
        if (customer == null) {
            response.put("message", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Thiết lập các thuộc tính cần thiết cho đối tượng Rating
            BusCompany busCompany = busCompanyService.getById(newRating.getBusCompany_id());
            Rating rating = new Rating(0, newRating.getAmount_star(), newRating.getRating_content(),
                    newRating.getRating_editTime(), customer, busCompany);
            ratingService.save(rating);

            response.put("message", "Rating saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to save rating");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Phương thức update
    @PutMapping(value = { "/update/{id}", "/update/{id}/" })
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> update(@PathVariable(name = "id") int ratingId, @RequestBody Rating updatedRating) {
        Rating rating = ratingService.getById(ratingId);
        if (rating == null) {
            return new ResponseEntity<>("Rating not found", HttpStatus.NO_CONTENT);
        }
        try {
            rating.setRating_content(updatedRating.getRating_content());
            rating.setAmount_star(updatedRating.getAmount_star());
            ratingService.save(rating);
            return new ResponseEntity<>("Rating updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = { "/{idBus}", "/{idBus}/" })
    public ResponseEntity<List<Rating>> getByIdBus(@PathVariable(name = "idBus") int idBus) {
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

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> deleteRating(@PathVariable(name = "id") int id) {
        try {
            ratingService.delete(id);
            return ResponseEntity.ok("Rating deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete rating");
        }
    }

    @GetMapping("/busCompany/{busCompanyId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByBusCompany(
            @PathVariable(name = "busCompanyId") int busCompanyId) {
        List<RatingDTO> ratings = ratingService.getRatingsByBusCompany(busCompanyId);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
