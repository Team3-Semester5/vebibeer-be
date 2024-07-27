package com.example.vebibeer_be.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Rating;
import java.util.*;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {
    @Query(value = "SELECT r.rating_id, r.amount_star, r.rating_content, r.rating_edit_time, " +
            "c.customer_fullname, c.username, b.bus_company_name, " +
            "car.car_code AS car_name, d.driver_name " +
            "FROM rating r " +
            "JOIN buscompany b ON r.bus_company_id = b.bus_company_id " +
            "JOIN customer c ON r.customer_id = c.customer_id " +
            "JOIN route rt ON r.bus_company_id = rt.bus_company_id " +
            "JOIN car ON rt.car_id = car.car_id " +
            "JOIN driver d ON rt.driver_id = d.driver_id " +
            "WHERE b.bus_company_id = :busCompanyId", nativeQuery = true)
    List<Object[]> findRatingsByBusCompany(@Param("busCompanyId") int busCompanyId);

}
