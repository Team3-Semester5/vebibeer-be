package com.example.vebibeer_be.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.repo.TransactionRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.dto.TransactionDetailDTO;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TicketRepo ticketRepo;

    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    public Transaction getById(int transaction_id) {
        return transactionRepo.getReferenceById(transaction_id);
    }

    public void save(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    public void delete(int transaction_id) {
        transactionRepo.deleteById(transaction_id);
    }

    public List<TransactionDetailDTO> getTransactionInfoByCustomerId(int customerId) {
        List<Transaction> transactions = transactionRepo.findByCustomer(customerRepo.getReferenceById(customerId));
        List<TransactionDetailDTO> transactionDetails = new ArrayList<>();

        for (Transaction transaction : transactions) {
            int transaction_id = transaction.getTransaction_id();
            String startLocation = "";
            String endLocation = "";
            String busCompany = "";
            double totalTicketPrice = 0;
            int totalTickets = 0;
            String paymentMethod = transaction.getPaymentMethod() != null
                    ? transaction.getPaymentMethod().getPaymentMethod_name()
                    : "";
            String transactionStatus = transaction.getTransaction_status();
            String voucherCode = transaction.getVoucher() != null ? transaction.getVoucher().getVoucher_code() : "";
            double saleUp = transaction.getVoucher() != null ? transaction.getVoucher().getSaleUp() : 0;
            int points = transaction.getTransaction_point();
            StringBuilder ticketSeats = new StringBuilder();
            String routeStartTime = "";
            String routeEndTime = "";
            String ticketIds = "";

            for (Ticket ticket : transaction.getTickets()) {
                if (startLocation.isEmpty() && ticket.getRoute().getStartLocation() != null) {
                    startLocation = ticket.getRoute().getStartLocation().getLocation_name();
                }
                if (endLocation.isEmpty() && ticket.getRoute().getEndLocation() != null) {
                    endLocation = ticket.getRoute().getEndLocation().getLocation_name();
                }
                if (busCompany.isEmpty() && ticket.getRoute().getBusCompany() != null) {
                    busCompany = ticket.getRoute().getBusCompany().getBusCompany_fullname();
                }
                totalTicketPrice += ticket.getTicket_price();
                totalTickets++;
                if (ticketSeats.length() > 0) {
                    ticketSeats.append(", ");
                }
                ticketIds += ticket.getTicket_id() + ",";
                ticketSeats.append(ticket.getTicket_seat());
                if (routeStartTime.isEmpty() && ticket.getRoute().getRoute_startTime() != null) {
                    routeStartTime = ticket.getRoute().getRoute_startTime().toString();
                }
                if (routeEndTime.isEmpty() && ticket.getRoute().getRoute_endTime() != null) {
                    routeEndTime = ticket.getRoute().getRoute_endTime().toString();
                }
            }

            TransactionDetailDTO detailDTO = new TransactionDetailDTO(
                    startLocation,
                    endLocation,
                    busCompany,
                    totalTicketPrice,
                    totalTickets,
                    paymentMethod,
                    transactionStatus,
                    voucherCode,
                    saleUp,
                    points,
                    ticketSeats.toString(),
                    routeStartTime,
                    routeEndTime);
            detailDTO.setTicketIds(ticketIds);
            detailDTO.setTransaction_id(transaction_id);
            detailDTO.setCustomer_id(customerId);
            transactionDetails.add(detailDTO);
        }

        return transactionDetails;
    }

    private double calculateRefund(LocalDateTime timeNow, LocalDateTime timeStart) {
        int comparisonResult = timeNow.compareTo(timeStart);
        if (comparisonResult <= 48 && comparisonResult > 24) {
            return 0.90;
        } else if (comparisonResult <= 24 && comparisonResult > 2) {
            return 0.65;
        } else if (comparisonResult <= 2 && comparisonResult > 0) {
            return 0.30;
        } else if (comparisonResult < 0) {
            return 0;
        } else {
            return 1.00;
        }
    }

    public Transaction cancelTransaction(TransactionDetailDTO transactionDetailDTO) {
        Transaction transaction = transactionRepo.getReferenceById(transactionDetailDTO.getTransaction_id());
        transaction.setTransaction_status("Pending");
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime timeStart = LocalDateTime.parse(transactionDetailDTO.getRouteStartTime(), formatter);
        Customer customer = customerRepo.getReferenceById(transactionDetailDTO.getCustomer_id());
        customer.setPoint(customer.getPoint()
                + (long) (transactionDetailDTO.getTotalTicketPrice() * calculateRefund(timeNow, timeStart)));
        customerRepo.save(customer);
        String[] tickets = transactionDetailDTO.getTicketIds().split(","); // 131,152, -> 2
        for (int i = 0; i < tickets.length; i++) {
            System.out.println(tickets[i]);
            Ticket ticket = ticketRepo.getReferenceById(Integer.parseInt(tickets[i]));
            ticket.setTicket_status("Empty");
            ticketRepo.save(ticket);
        }
        ZoneId zoneId = ZoneId.systemDefault();
        
        // Convert LocalDateTime to ZonedDateTime
        ZonedDateTime zonedDateTime = timeNow.atZone(zoneId);
        
        // Convert ZonedDateTime to Instant
        Timestamp nowTimestamp = Timestamp.from(zonedDateTime.toInstant());
        transaction.setTransaction_timeEdit(nowTimestamp);
        return transactionRepo.save(transaction);
    }

    // public Transaction confirmCancel()
}