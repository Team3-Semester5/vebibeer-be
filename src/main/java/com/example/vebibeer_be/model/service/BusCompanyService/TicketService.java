package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;


@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;

    public List<Ticket> getAll() {
        return ticketRepo.findAll();
    }

    public Ticket getById(int ticket_id) {
        return ticketRepo.getReferenceById(ticket_id);
    }

    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public void delete(int ticket_id) {
        ticketRepo.deleteById(ticket_id);
        ;
    }
    public double getAverageTicketPrice() {
        List<Ticket> tickets = getAll();
        OptionalDouble average = tickets.stream()
                                        .mapToDouble(Ticket::getTicket_price)
                                        .average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

}
