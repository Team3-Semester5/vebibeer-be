package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Ticket;
import com.example.model.repo.TicketRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    // Create a new ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Retrieve all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Retrieve a ticket by its ID
    public Optional<Ticket> getTicketById(int id) {
        return ticketRepository.findById(id);
    }

    // Update a ticket
    public Ticket updateTicket(int id, Ticket ticketDetails) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket existingTicket = optionalTicket.get();
            existingTicket.setRoute(ticketDetails.getRoute());
            existingTicket.setTicket_price(ticketDetails.getTicket_price());
            existingTicket.setTicket_seat(ticketDetails.getTicket_seat());
            existingTicket.setTicket_status(ticketDetails.getTicket_status());
            return ticketRepository.save(existingTicket);
        } else {
            throw new RuntimeException("Ticket not found with id " + id);
        }
    }

    // Delete a ticket by its ID
    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }
}
