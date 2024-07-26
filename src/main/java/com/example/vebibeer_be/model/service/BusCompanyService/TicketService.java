package com.example.vebibeer_be.model.service.BusCompanyService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vebibeer_be.dto.TicketDTO;
import com.example.vebibeer_be.exception.TicketAlreadyBookedException;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private static final ConcurrentHashMap<String, Long> seatLocks = new ConcurrentHashMap<>();

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

    public double getLowestTicketPriceByRouteId(int routeId) {
        return ticketRepo.findMinTicketPriceByRouteId(routeId).orElse(0.0);
    }

    public double getHighestTicketPriceByRouteId(int routeId) {
        return ticketRepo.findMaxTicketPriceByRouteId(routeId).orElse(0.0);
    }

    @Transactional
    public synchronized boolean lockSeat(String ticketSeat, String customerName) {
        long currentTime = System.currentTimeMillis();
        if (seatLocks.containsKey(ticketSeat)) {
            return false;
        }
        seatLocks.put(ticketSeat, currentTime);

        // Simulate unlocking after 5 minutes
        new Thread(() -> {
            try {
                Thread.sleep(300000); // 5 minutes
                if (seatLocks.get(ticketSeat) == currentTime) {
                    seatLocks.remove(ticketSeat);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return true;
    }

    @Transactional
    public void bookTicket(String ticketSeat, String customerName) {
        Long lockTime = seatLocks.get(ticketSeat);
        if (lockTime == null || System.currentTimeMillis() - lockTime > 300000) {
            throw new RuntimeException("Seat not locked or lock expired");
        }

        try {
            Ticket ticket = ticketRepo.findBySeatForUpdate(ticketSeat).orElseThrow(() -> new RuntimeException("Ticket not found"));
            if ("Empty".equals(ticket.getTicket_status())) {
                ticket.setTicket_status("Booked");
                seatLocks.remove(ticketSeat);
                ticketRepo.save(ticket);
            } else {
                throw new TicketAlreadyBookedException("Ticket is already booked");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to book ticket due to concurrent booking attempt or timeout. Please try again.", e);
        }
    }

    @Transactional
    public synchronized void unlockSeat(String ticketSeat) {
        seatLocks.remove(ticketSeat);
    }
    
    public TicketDTO getTicketBySeat(String ticketSeat) {
        return ticketRepo.findBySeat(ticketSeat)
                         .map(this::convertToDTO)
                         .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setTicket_id(ticket.getTicket_id());
        dto.setTicket_price(ticket.getTicket_price());
        dto.setTicket_seat(ticket.getTicket_seat());
        dto.setTicket_status(ticket.getTicket_status());
        dto.setRoute(ticket.getRoute());
        return dto;
    }
    
}
