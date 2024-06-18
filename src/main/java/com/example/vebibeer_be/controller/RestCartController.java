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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.dto.TicketDTO;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.service.BusCompanyService.TicketService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class RestCartController {
    @Autowired
    TicketService ticketService = new TicketService();

    private TicketDTO convertToDto(Ticket ticket) {
        return new TicketDTO(ticket.getTicket_id(), ticket.getTicket_price(), ticket.getTicket_seat(),
                ticket.getTicket_status(), ticket.getRoute());
    }

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<TicketDTO>> getCart(HttpSession session) {
        List<Ticket> cart = new ArrayList<>();
        try {
            cart = (List<Ticket>) session.getAttribute("cart");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<TicketDTO> cartDto = new ArrayList<>();
        for (Ticket ticket : cart) {
            cartDto.add(convertToDto(ticket));
        }
        System.out.println(cartDto.toString());
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping(value = { "/{idTicket}", "/{idTicket}/" })
    public ResponseEntity<List<TicketDTO>> addToCart(HttpSession session, @PathVariable int idTicket) {
        List<Ticket> cart = (List<Ticket>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        Ticket ticket = ticketService.getById(idTicket);
        cart.add(ticket);
        session.setAttribute("cart", cart);
        List<TicketDTO> cartDto = new ArrayList<>();
        for (Ticket t : cart) {
            cartDto.add(convertToDto(t));
        }
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/{idTicket}", "/{idTicket}/" })
    public ResponseEntity<List<TicketDTO>> deleteFromCart(HttpSession session,
            @PathVariable int idTicket) {
        List<Ticket> cart = (List<Ticket>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        Ticket ticket = ticketService.getById(idTicket);
        cart.remove(ticket);
        session.setAttribute("cart", cart);
        List<TicketDTO> cartDto = new ArrayList<>();
        for (Ticket t : cart) {
            cartDto.add(convertToDto(t));
        }
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
