package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.dto.RouteDTO;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.BusCompany.Car;
import com.example.vebibeer_be.model.entities.BusCompany.Driver;
import com.example.vebibeer_be.model.entities.BusCompany.Location;
import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.CarRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.DriverRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.LocationRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.RouteRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;

@Service
public class RouteService {
    @Autowired
    RouteRepo routeRepo;

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    BusCompanyRepo busCompanyRepo;

    @Autowired
    LocationRepo locationRepo;

    public List<Route> getAll() {
        return routeRepo.findAll();
    }

    public Route getById(int route_id) {
        return routeRepo.getReferenceById(route_id);
    }

    public void save(RouteDTO newRoute) {
        Car car = carRepo.getReferenceById(newRoute.getCar_id());
        BusCompany busCompany = busCompanyRepo.getReferenceById(newRoute.getBusCompany_id());
        Location starLocation = locationRepo.getReferenceById(newRoute.getStartLocation_id());
        Location endLocation = locationRepo.getReferenceById(newRoute.getEndLocation_id());
        Driver driver = driverRepo.getReferenceById(newRoute.getDriver_id());
        int amountSeat = car.getAmount_seat();
        Route route = new Route(0, newRoute.getRoute_startTime(), newRoute.getRoute_endTime(), newRoute.getPolicy(), newRoute.getRoute_description(), busCompany, starLocation, endLocation, car, driver, null);
        Route addRoute = routeRepo.save(route);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < amountSeat/2; j++) {
                String ticketSeat = i == 0 ? "A" : "B";
                ticketSeat += j;
                int price = newRoute.getPriceTicket();
                ticketRepo.save(new Ticket(0, price, ticketSeat, "Empty", addRoute));
            }
        }
    }

    // public void save(Route route){
    //     routeRepo.save(route);
    // }

    public void delete(int route_id) {
        routeRepo.deleteById(route_id);
    }

    public List<Route> getRoutes(String startCity, String endCity, String date) {
        return routeRepo.findRoutesByStartAndEndCityAndDate(startCity, endCity, date);
    }
}
