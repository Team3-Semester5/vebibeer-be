package com.example.vebibeer_be.model.service.BusCompanyService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    CarRepo carRepo;

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    BusCompanyRepo busCompanyRepo;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    LocationRepo locationRepo;

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

    public List<Route> getRoutes(String startCity, String endCity, Timestamp date) {
        return routeRepo.findRoutesByStartAndEndCityAndDate(startCity, endCity, date);
    }

    public List<Route> getRoutesByBusCompanyId(int busCompanyId) {
        return routeRepo.findByBusCompanyId(busCompanyId);
    }

    public Route update(int route_id, RouteDTO updatedRoute) {
        try {
            Route route = routeRepo.findById(route_id)
                    .orElseThrow(() -> new RuntimeException("Route not found with id " + route_id));

            Car car = carRepo.getReferenceById(updatedRoute.getCar_id());

            Location startLocation = locationRepo.getReferenceById(updatedRoute.getStartLocation_id());
            Location endLocation = locationRepo.getReferenceById(updatedRoute.getEndLocation_id());
            Driver driver = driverRepo.getReferenceById(updatedRoute.getDriver_id());
            BusCompany busCompany = busCompanyRepo.getReferenceById(updatedRoute.getBusCompany_id());
            route.setCar(car);
            route.setDriver(driver);
            route.setStartLocation(startLocation);
            route.setEndLocation(endLocation);
            route.setRoute_startTime(updatedRoute.getRoute_startTime());
            route.setRoute_endTime(updatedRoute.getRoute_endTime());
            route.setPolicy(updatedRoute.getPolicy());
            route.setRoute_description(updatedRoute.getRoute_description());
            route.setBusCompany(busCompany);
            List<Ticket> tickets = new ArrayList<>();
            int totalSeats = car.getAmount_seat();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < totalSeats / 2; j++) {
                    String ticketSeat = i == 0 ? "A" : "B";
                    ticketSeat += j;
                    int price = updatedRoute.getPriceTicket();
                    Ticket ticket = new Ticket(0, price, ticketSeat, "Empty", route);
                    tickets.add(ticket);
                }
            }
            ticketRepo.saveAll(tickets);

            return routeRepo.save(route);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void save(RouteDTO newRoute) {
        System.out.println(newRoute.toString());
        Car car = carRepo.getReferenceById(newRoute.getCar_id());
        BusCompany busCompany = busCompanyRepo.getReferenceById(newRoute.getBusCompany_id());
        Location starLocation = locationRepo.getReferenceById(newRoute.getStartLocation_id());
        Location endLocation = locationRepo.getReferenceById(newRoute.getEndLocation_id());
        Driver driver = driverRepo.getReferenceById(newRoute.getDriver_id());
        int amountSeat = car.getAmount_seat();
        Route route = new Route(0, newRoute.getRoute_startTime(), newRoute.getRoute_endTime(), newRoute.getPolicy(),
                newRoute.getRoute_description(), newRoute.isDaily(), busCompany, starLocation, endLocation, car, driver,
                null);
        Route addRoute = routeRepo.save(route);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < amountSeat / 2; j++) {
                String ticketSeat = i == 0 ? "A" : "B";
                ticketSeat += j;
                int price = newRoute.getPriceTicket();
                ticketRepo.save(new Ticket(0, price, ticketSeat, "Empty", addRoute));
            }
        }
    }

    @Scheduled(cron = "0 24 23 * * ?")
    @Transactional
    public void createRouteDaily() {
        List<Route> routes = routeRepo.findAll();
        for (Route route : routes) {
            if (route.isDaily()) {
                Timestamp startTime = new Timestamp(route.getRoute_startTime().getTime() + (24 * 60 * 60 * 1000));
                Timestamp endTime = new Timestamp(route.getRoute_endTime().getTime() + (24 * 60 * 60 * 1000));
                Route newRoute = new Route(0, startTime, endTime,
                        route.getPolicy(),
                        route.getRoute_description(), route.isDaily(), route.getBusCompany(), route.getStartLocation(),
                        route.getEndLocation(), route.getCar(),
                        route.getDriver(), null);

                Route addRoute = routeRepo.save(newRoute);
                route.setDaily(false);
                routeRepo.save(route);
                System.out.println(addRoute.toString());
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < route.getCar().getAmount_seat() / 2; j++) {
                        String ticketSeat = i == 0 ? "A" : "B";
                        ticketSeat += j;
                        int price = 150;
                        ticketRepo.save(new Ticket(0, price, ticketSeat, "Empty", addRoute));
                    }
                }
            }
        }
    }
}
