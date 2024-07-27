package com.example.vebibeer_be.model.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.dto.RatingDTO;
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

   /**
 * @param busCompanyId
 * @return
 */
public List<RatingDTO> getRatingsByBusCompany(int busCompanyId) {
        List<Object[]> results = ratingRepo.findRatingsByBusCompany(busCompanyId);
        return results.stream().map(result -> new RatingDTO(
            (Integer) result[0], // rating_id
            (String) result[2],  // rating_content
            (Timestamp) result[3], // rating_editTime
            (Integer) result[1], // amount_star
            (String) result[5], // username
            busCompanyId,       // busCompany_id
            (String) result[7], // carCode
            (String) result[8], // driverName
            (String) result[4]  // customerFullname
        )).collect(Collectors.toList());
    }

}
