package com.example.vebibeer_be.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.service.BusCompanyService.RouteService;

@RestController
@RequestMapping("/api/routes")
public class RestRouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping("/")
    public Route createRoute(@RequestBody Route route) {
        return routeService.createRoute(route);
    }
    
    @GetMapping("/")
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable int id) {
        Optional<Route> route = routeService.getRouteById(id);
        if (route.isPresent()) {
            return ResponseEntity.ok(route.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable int id, @RequestBody Route routeDetails) {
        try {
            Route updatedRoute = routeService.updateRoute(id, routeDetails);
            return ResponseEntity.ok(updatedRoute);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable int id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
