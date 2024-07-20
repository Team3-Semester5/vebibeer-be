package com.example.vebibeer_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionDetailDTO {
    private int transaction_id;
    private String startLocation;
    private String endLocation;
    private String busCompany;
    private double totalTicketPrice;
    private int totalTickets; // số vé đã đặt
    private String paymentMethod;
    private String transactionStatus;
    private String voucherCode;
    private double saleUp;
    private int points;
    private String ticketSeats;
    private String routeStartTime;
    private String routeEndTime;
    private int customer_id;
    private String ticketIds;

    public TransactionDetailDTO(String startLocation, String endLocation, String busCompany, double totalTicketPrice,
                                int totalTickets, String paymentMethod, String transactionStatus, String voucherCode,
                                double saleUp, int points, String ticketSeats, String routeStartTime, String routeEndTime) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.busCompany = busCompany;
        this.totalTicketPrice = totalTicketPrice;
        this.totalTickets = totalTickets;
        this.paymentMethod = paymentMethod;
        this.transactionStatus = transactionStatus;
        this.voucherCode = voucherCode;
        this.saleUp = saleUp;
        this.points = points;
        this.ticketSeats = ticketSeats;
        this.routeStartTime = routeStartTime;
        this.routeEndTime = routeEndTime;
    }

    // Getters and setters
}