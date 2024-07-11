package com.example.vebibeer_be.model.repo.BusCompanyRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    @Query("SELECT MIN(t.ticket_price) FROM Ticket t WHERE t.route.route_id = :routeId")
    Optional<Double> findMinTicketPriceByRouteId(@Param("routeId") int routeId);

    @Query("SELECT MAX(t.ticket_price) FROM Ticket t WHERE t.route.route_id = :routeId")
    Optional<Double> findMaxTicketPriceByRouteId(@Param("routeId") int routeId);
}
