package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.RouteRepo;


@Service
public class RouteService {

    @Autowired
    private RouteRepo routeRepo;

    // Create a new Route
    public Route createRoute(Route route) {
        return routeRepo.save(route);
    }

    // Retrieve all Routes
    public List<Route> getAllRoutes() {
        return routeRepo.findAll();
    }

    // Retrieve a Route by ID
    public Optional<Route> getRouteById(int id) {
        return routeRepo.findById(id);
    }

    // Update an existing Route
    public Route updateRoute(int id, Route routeDetails) {
        return routeRepo.findById(id).map(existingRoute -> {
            existingRoute.setBusCompany(routeDetails.getBusCompany());
            existingRoute.setRoute_startTime(routeDetails.getRoute_startTime());
            existingRoute.setRoute_endTime(routeDetails.getRoute_endTime());
            existingRoute.setPolicy(routeDetails.getPolicy());
            existingRoute.setRoute_description(routeDetails.getRoute_description());
            existingRoute.setStartLocation(routeDetails.getStartLocation());
            existingRoute.setEndLocation(routeDetails.getEndLocation());
            existingRoute.setCar(routeDetails.getCar());
            existingRoute.setDriver(routeDetails.getDriver());
            existingRoute.setServices(routeDetails.getServices());
            return routeRepo.save(existingRoute);
        }).orElseThrow(() -> new RuntimeException("Route not found with id " + id));
    }

    // Delete a Route by ID
    public void deleteRoute(int id) {
        if (!routeRepo.existsById(id)) {
            throw new RuntimeException("Route not found with id " + id);
        }
        routeRepo.deleteById(id);
    }
}