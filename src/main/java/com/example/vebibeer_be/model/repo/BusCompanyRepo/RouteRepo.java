package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.vebibeer_be.model.entities.BusCompany.Route;

@Repository
public interface RouteRepo extends JpaRepository<Route, Integer> {

        @Query(value = "SELECT r.* FROM route r, location l_start, location l_end " +
                        "WHERE r.start_location_id = l_start.location_id " +
                        "AND r.end_location_id = l_end.location_id " +
                        "AND l_start.location_name = :startCity " +
                        "AND l_end.location_name = :endCity " +
                        "AND DATE(r.route_start_time) = :date", nativeQuery = true)
        List<Route> findRoutesByStartAndEndCityAndDate(@Param("startCity") String startCity,
                        @Param("endCity") String endCity,
                        @Param("date") Timestamp date);

        @Query("SELECT r FROM Route r WHERE r.busCompany.busCompany_id = :busCompanyId")
        List<Route> findByBusCompanyId(@Param("busCompanyId") int busCompanyId);

}