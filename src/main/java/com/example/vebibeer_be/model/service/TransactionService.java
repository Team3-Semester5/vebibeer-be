package com.example.vebibeer_be.model.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.repo.TransactionRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.dto.TransactionDetailDTO;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired 
    CustomerRepo customerRepo;

    public List<Transaction> getAll(){
        return transactionRepo.findAll();
    }

    public Transaction getById(int transaction_id){
        return transactionRepo.getReferenceById(transaction_id);
    }

    public void save(Transaction transaction){
        transactionRepo.save(transaction);
    }

    public void delete(int transaction_id){
        transactionRepo.deleteById(transaction_id);
    }

    public List<TransactionDetailDTO> getTransactionInfoByCustomerId(int customerId) {
        List<Transaction> transactions = transactionRepo.findByCustomer(customerRepo.getReferenceById(customerId));
        List<TransactionDetailDTO> transactionDetails = new ArrayList<>();
        
        for (Transaction transaction : transactions) {
            String startLocation = "";
            String endLocation = "";
            String busCompany = "";
            double totalTicketPrice = 0;
            int totalTickets = 0;
            String paymentMethod = transaction.getPaymentMethod() != null ? transaction.getPaymentMethod().getPaymentMethod_name() : "";
            String transactionStatus = transaction.getTransaction_status();
            String voucherCode = transaction.getVoucher() != null ? transaction.getVoucher().getVoucher_code() : "";
            double saleUp = transaction.getVoucher() != null ? transaction.getVoucher().getSaleUp() : 0;
            int points = transaction.getTransaction_point();
            StringBuilder ticketSeats = new StringBuilder();
            String routeStartTime = "";
            String routeEndTime = "";

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
                    routeEndTime
            );
            transactionDetails.add(detailDTO);
        }
        
        return transactionDetails;
    }
}