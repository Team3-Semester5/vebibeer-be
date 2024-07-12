package com.example.vebibeer_be.model.entities.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerTransactionDetail {
    private Integer customerId;
    private String username;
    private String customerFullName;
    private LocalDate customerDOB; // Changed to LocalDate
    private String customerDescription;
    private String customerGender;
    private String customerNationality;

    // Transaction fields
    private String transactionStatus;
    private LocalDateTime transactionTimeEdit;
    private Double transactionVAT;

    // Payment Method
    private String paymentMethodName;

    // Ticket fields
    private Double ticketPrice;
    private String ticketSeat;
    private String ticketStatus;

    // Route fields
    private LocalDateTime routeStartTime;
    private LocalDateTime routeEndTime;

    // Bus Company fields
    private String busCompanyName;

}