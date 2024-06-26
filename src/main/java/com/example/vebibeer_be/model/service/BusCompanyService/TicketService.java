package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;

    public List<Ticket> getAll() {
        return ticketRepo.findAll();
    }

    public Ticket getById(int ticketId) {
        return ticketRepo.findById(ticketId).orElse(null);
    }

    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public void delete(int ticketId) {
        ticketRepo.deleteById(ticketId);
    }

    public double getLowestTicketPrice() {
        return ticketRepo.findAll().stream()
                         .mapToDouble(Ticket::getTicket_price)
                         .min()
                         .orElse(0.0);
    }

    public double getHighestTicketPrice() {
        return ticketRepo.findAll().stream()
                         .mapToDouble(Ticket::getTicket_price)
                         .max()
                         .orElse(0.0);
    }
}