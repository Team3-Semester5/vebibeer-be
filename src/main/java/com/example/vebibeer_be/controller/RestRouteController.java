package com.example.vebibeer_be.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.dto.RouteDTO;
import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.service.BusCompanyService.RouteService;

@RestController
@RequestMapping("/route")
public class RestRouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Route>> showList() {
        List<Route> routes = routeService.getAll();
        if (routes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @PostMapping(value = { "/buscomapany/save", "/buscomapany/save/" })
    public ResponseEntity<?> save(@RequestBody RouteDTO newRoute) {
        routeService.save(newRoute);
        return ResponseEntity.ok(newRoute);
    }

    @GetMapping(value = { "/buscompany/{id}", "/buscompany/{id}/" })
    public ResponseEntity<Route> getById(@PathVariable(name = "id") int route_id) {
        Route route = routeService.getById(route_id);
        if (route == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/buscompany/delete/{id}", "/buscompany/delete/{id}/" })
    public ResponseEntity<Route> delete(@PathVariable(name = "id") int route_id) {
        Route route = routeService.getById(route_id);
        if (route == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        routeService.delete(route_id);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @PutMapping("/buscompany/update/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable(name = "id") int id, @RequestBody RouteDTO updatedRouteDTO) {
        try {
            Route updatedRoute = routeService.update(id, updatedRouteDTO);
            return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found with id: " + id);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Route>> getRoutesByCitiesAndDate(
            @RequestParam("startCity") String startCity,
            @RequestParam("endCity") String endCity,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateString) {
                List<Route> routes = new ArrayList<>();
        try {
            // Convert String to LocalDate
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
            // Convert LocalDate to Timestamp
            Timestamp dateTimestamp = Timestamp.valueOf(date.atStartOfDay());
            routes = routeService.getRoutes(startCity, endCity, dateTimestamp);
        } catch (Exception e) {
            routes = routeService.getAll();
            return new ResponseEntity<>(routes, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @GetMapping("/buscompany/{busCompanyId}/routes")
    public ResponseEntity<List<Route>> getRoutesByBusCompanyId(@PathVariable int busCompanyId) {
        List<Route> routes = routeService.getRoutesByBusCompanyId(busCompanyId);
        return routes.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(routes, HttpStatus.OK);
    }

}
