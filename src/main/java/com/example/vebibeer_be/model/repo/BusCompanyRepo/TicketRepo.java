package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;

import jakarta.persistence.LockModeType;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.ticket_seat = :ticket_seat AND t.route.route_id = :routeId")
    Optional<Ticket> findBySeatForUpdate(@Param("ticket_seat") String ticket_seat, @Param("routeId") int routeId);

    @Query("SELECT t FROM Ticket t WHERE t.ticket_seat = :ticketSeat AND t.route.route_id = :routeId")
    Optional<Ticket> findBySeat(@Param("ticketSeat") String ticketSeat, @Param("routeId") int routeId);

    @Query("SELECT MIN(t.ticket_price) FROM Ticket t WHERE t.route.route_id = :routeId")
    Optional<Double> findMinTicketPriceByRouteId(@Param("routeId") int routeId);

    @Query("SELECT MAX(t.ticket_price) FROM Ticket t WHERE t.route.route_id = :routeId")
    Optional<Double> findMaxTicketPriceByRouteId(@Param("routeId") int routeId);
}