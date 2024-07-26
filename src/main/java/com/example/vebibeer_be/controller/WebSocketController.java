package com.example.vebibeer_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.vebibeer_be.dto.TicketDTO;
import com.example.vebibeer_be.dto.TicketLockRequest;
import com.example.vebibeer_be.model.service.BusCompanyService.TicketService;

@Controller
public class WebSocketController {

    @Autowired
    private TicketService ticketService;

    @MessageMapping("/lockSeat")
    @SendTo("/topic/seatStatus")
    public TicketDTO lockSeat(TicketLockRequest request) throws Exception {
        boolean lockSuccess = ticketService.lockSeat(request.getTicketSeat(), request.getCustomerName());
        TicketDTO ticket = ticketService.getTicketBySeat(request.getTicketSeat());
        ticket.setLockSuccess(lockSuccess);
        return ticket;
    }

    @MessageMapping("/bookSeat")
    @SendTo("/topic/seatStatus")
    public TicketDTO bookSeat(TicketLockRequest request) throws Exception {
        ticketService.bookTicket(request.getTicketSeat(), request.getCustomerName());
        return ticketService.getTicketBySeat(request.getTicketSeat());
    }

    @MessageMapping("/unlockSeat")
    @SendTo("/topic/seatStatus")
    public TicketDTO unlockSeat(TicketLockRequest request) throws Exception {
        ticketService.unlockSeat(request.getTicketSeat());
        return ticketService.getTicketBySeat(request.getTicketSeat());
    }
}
