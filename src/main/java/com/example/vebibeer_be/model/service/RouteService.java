package com.example.vebibeer_be.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entity.Route;
import com.example.vebibeer_be.model.repo.RouteRepository;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(int id) {
        return routeRepository.findById(id);
    }

    public Route updateRoute(int id, Route routeDetails) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
        if (optionalRoute.isPresent()) {
            Route existingRoute = optionalRoute.get();
            existingRoute.setBusCompany(routeDetails.getBusCompany());
            existingRoute.setStart_point(routeDetails.getStart_point());
            existingRoute.setEnd_point(routeDetails.getEnd_point());
            existingRoute.setStart_time(routeDetails.getStart_time());
            existingRoute.setEnd_time(routeDetails.getEnd_time());
            existingRoute.setRoute_policy(routeDetails.getRoute_policy());
            existingRoute.setRoute_description(routeDetails.getRoute_description());
            existingRoute.setCar(routeDetails.getCar());
            existingRoute.setDriver(routeDetails.getDriver());
            return routeRepository.save(existingRoute);
        } else {
            throw new RuntimeException("Route not found with id " + id);
        }
    }

    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }
}
