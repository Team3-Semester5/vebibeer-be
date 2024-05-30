package com.example.controller;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import com.example.model.entity.Ticket;
    import com.example.model.service.TicketService;

    @RestController
    @RequestMapping("/api/tickets")
    public class TicketController {
        
        @Autowired
        private TicketService ticketService;

        @PostMapping
        public Ticket createTicket(@RequestBody Ticket ticket) {
            return ticketService.createTicket(ticket);
        }

        @GetMapping
        public List<Ticket> getAllTickets() {
            return ticketService.getAllTickets();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
            Optional<Ticket> ticket = ticketService.getTicketById(id);
            if (ticket.isPresent()) {
                return ResponseEntity.ok(ticket.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
            try {
                Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
                return ResponseEntity.ok(updatedTicket);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        }
    }
