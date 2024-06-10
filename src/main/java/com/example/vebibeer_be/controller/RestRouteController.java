package com.example.vebibeer_be.controller;

import java.util.List;

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

import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.service.BusCompanyService.RouteService;

@RestController
@RequestMapping("/route")
public class RestRouteController {
    @Autowired
    RouteService routeService = new RouteService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Route>> showList() {
        List<Route> routes = routeService.getAll();
        if (routes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Route>>(routes, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Route> save(@RequestBody Route newRoute) {
        if (newRoute == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Route Route = routeService.getById(newRoute.getRoute_id());
        if (Route == null) {
            routeService.save(Route);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        routeService.save(Route);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Route> getById(@PathVariable(name = "id")int route_id) {
        Route route = routeService.getById(route_id);
        if (route == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Route>(route, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Route> delete(@PathVariable(name = "id") int route_id){
        Route route = routeService.getById(route_id);
        if (route == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        routeService.delete(route_id);
        return new ResponseEntity<Route>(route, HttpStatus.OK);
    }

}