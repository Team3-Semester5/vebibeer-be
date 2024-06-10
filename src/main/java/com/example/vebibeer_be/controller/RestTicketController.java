package com.example.vebibeer_be.controller;


import java.util.ArrayList;
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

import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.service.BusCompanyService.TicketService;

@RestController
@RequestMapping("/tickets")
public class RestTicketController {
    @Autowired
    TicketService ticketService = new TicketService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Ticket>> showList() {
        List<Ticket> Tickets = ticketService.getAll();
        if (Tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ticket>>(Tickets, HttpStatus.OK);
    }

    @GetMapping(value = {"/{idRoute}", "/{idRoute}/"})
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
    
    @PostMapping(value = {"/save", "/save/"})
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
    
    // @GetMapping(value = {"/{id}", "/{id}/"})
    // public ResponseEntity<Ticket> getById(@PathVariable(name = "id")int Ticket_id) {
    //     Ticket Ticket = ticketService.getById(Ticket_id);
    //     if (Ticket == null) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<Ticket>(Ticket, HttpStatus.OK);
    // }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Ticket> delete(@PathVariable(name = "id") int Ticket_id){
        Ticket Ticket = ticketService.getById(Ticket_id);
        if (Ticket == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ticketService.delete(Ticket_id);
        return new ResponseEntity<Ticket>(Ticket, HttpStatus.OK);
    }
}