package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.RatingService;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/rating")
public class RestRatingController {
    @Autowired
    RatingService ratingService;

    @Autowired 
    CustomerService customerService;

    @Autowired 
    BusCompanyService busCompanyService;

    // Method to check if the user is logged in
    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Rating>> showList(HttpSession session) {
        if (!isUserLoggedIn(session)) {
            session.setAttribute("user", "tandung1"); // Simulate login
        }

        List<Rating> ratings = ratingService.getAll();
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }




    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, String>> save(@RequestBody Rating newRating, HttpSession session) {
        session.setAttribute("user", "tandung1");
        String username = (String) session.getAttribute("user");
        Map<String, String> response = new HashMap<>();
    
        if (username == null) {
            response.put("message", "User not logged in");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            response.put("message", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    
        try {
            // Thiết lập các thuộc tính cần thiết cho đối tượng Rating
            newRating.setCustomer(customer);
            newRating.setBusCompany(busCompanyService.getById(newRating.getBusCompany().getBusCompany_id()));
            ratingService.save(newRating);
    
            response.put("message", "Rating saved successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to save rating");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     // Phương thức update
     @PutMapping(value = {"/update/{id}", "/update/{id}/"})
     @CrossOrigin(origins = "http://localhost:3000")
     public ResponseEntity<String> update(@PathVariable(name = "id") int ratingId, @RequestBody Rating updatedRating, HttpSession session) {
         if (!isUserLoggedIn(session)) {
             session.setAttribute("user", "tandung1"); // Simulate login
         }
 
         Rating rating = ratingService.getById(ratingId);
         if (rating == null) {
             return new ResponseEntity<>("Rating not found", HttpStatus.NO_CONTENT);
         }
         try {
             rating.setRating_content(updatedRating.getRating_content());
             ratingService.save(rating);
             return new ResponseEntity<>("Rating updated successfully", HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>("Failed to update rating", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
    


    @GetMapping(value = {"/{idBus}", "/{idBus}/"})
public ResponseEntity<List<Rating>> getByIdBus(@PathVariable(name = "idBus") int idBus, HttpSession session) {
    if (!isUserLoggedIn(session)) {
        session.setAttribute("user", "tandung1"); // Simulate login
    }

    List<Rating> ratings = new ArrayList<>();
    for (Rating rating : ratingService.getAll()) {
        if (rating.getBusCompany() != null && rating.getBusCompany().getBusCompany_id() == idBus) {
            ratings.add(rating);
        }
    }
    if (ratings.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(ratings, HttpStatus.OK);
}


    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<String> delete(@PathVariable(name = "id") int ratingId, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            session.setAttribute("user", "tandung1"); // Simulate login
        }

        Rating rating = ratingService.getById(ratingId);
        if (rating == null) {
            return new ResponseEntity<>("Rating not found", HttpStatus.NO_CONTENT);
        }
        try {
            ratingService.delete(ratingId);
            return new ResponseEntity<>("Rating deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
