package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vebibeer_be.exception.TicketAlreadyBookedException;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.service.BusCompanyService.TicketService;

@RestController
@RequestMapping("/tickets")
public class RestTicketController {
    @Autowired
    TicketService ticketService = new TicketService();

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Ticket>> showList() {
        List<Ticket> Tickets = ticketService.getAll();
        if (Tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ticket>>(Tickets, HttpStatus.OK);
    }

    @GetMapping(value = { "/{idRoute}", "/{idRoute}/" })
    public ResponseEntity<List<Ticket>> showListByRoute(@PathVariable(name = "idRoute") int idRoute) {
        List<Ticket> Tickets = new ArrayList<>();
        for (Ticket ticket : ticketService.getAll()) {
            if (ticket.getRoute().getRoute_id() == idRoute) {
                Tickets.add(ticket);
            }
        }
        if (Tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ticket>>(Tickets, HttpStatus.OK);
    }

    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<Ticket> save(@RequestBody Ticket newTicket) {
        if (newTicket == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Ticket Ticket = ticketService.getById(newTicket.getTicket_id());
        if (Ticket == null) {
            ticketService.save(Ticket);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        ticketService.save(Ticket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<Ticket> delete(@PathVariable(name = "id") int Ticket_id) {
        Ticket Ticket = ticketService.getById(Ticket_id);
        if (Ticket == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ticketService.delete(Ticket_id);
        return new ResponseEntity<Ticket>(Ticket, HttpStatus.OK);
    }

    @GetMapping("/lowest-price/{idRoute}/")
    public ResponseEntity<Double> getLowestTicketPrice(@PathVariable(name = "idRoute") int idRoute) {
        double lowestPrice = ticketService.getLowestTicketPriceByRouteId(idRoute);
        return new ResponseEntity<>(lowestPrice, HttpStatus.OK);
    }

    @GetMapping("/highest-price/{idRoute}/")
    public ResponseEntity<Double> getHighestTicketPrice(@PathVariable(name = "idRoute") int idRoute) {
        double highestPrice = ticketService.getHighestTicketPriceByRouteId(idRoute);
        return new ResponseEntity<>(highestPrice, HttpStatus.OK);
    }

    @PostMapping("/lock")
    public ResponseEntity<String> lockSeat(@RequestParam String ticketSeat, @RequestParam int routeId, @RequestParam String customerName) {
        try {
            boolean lockSuccess = ticketService.lockSeat(ticketSeat, routeId, customerName);
            if (lockSuccess) {
                return new ResponseEntity<>("Seat locked successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Seat is already locked by another customer", HttpStatus.CONFLICT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to lock seat. Please try again.", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestParam String ticketSeat, @RequestParam int routeId, @RequestParam String customerName) {
        try {
            ticketService.bookTicket(ticketSeat, routeId, customerName);
            return new ResponseEntity<>("Ticket booked successfully", HttpStatus.OK);
        } catch (TicketAlreadyBookedException e) {
            return new ResponseEntity<>("Ticket is already booked", HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    "Failed to book ticket due to concurrent booking attempt or timeout. Please try again.",
                    HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockSeat(@RequestParam String ticketSeat, @RequestParam int routeId) {
        ticketService.unlockSeat(ticketSeat, routeId);
        return new ResponseEntity<>("Seat unlocked successfully", HttpStatus.OK);
    }
}