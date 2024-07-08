package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.RouteRepo;

@Service
public class RouteService {
    @Autowired
    RouteRepo routeRepo;

    public List<Route> getAll() {
        return routeRepo.findAll();
    }

    public Route getById(int route_id) {
        return routeRepo.getReferenceById(route_id);
    }

    public void save(Route route) {
        routeRepo.save(route);
    }

    public void delete(int route_id) {
        routeRepo.deleteById(route_id);
    }

    public List<Route> getRoutes(String startCity, String endCity, String date) {
        return routeRepo.findRoutesByStartAndEndCityAndDate(startCity, endCity, date);
    }
}
