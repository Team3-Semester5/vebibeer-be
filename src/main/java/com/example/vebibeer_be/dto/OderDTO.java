package com.example.vebibeer_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OderDTO {
    private Integer transactionId;
    private String transactionStatus;
    private LocalDateTime transactionTimeEdit;
    private Integer customerId;
    private String username;
    private String customerFullName;
    private Integer paymentMethodId;
    private String paymentMethodName;
    private String ticketId;
    private Double ticketPrice;
    private String ticketSeat;
    private Integer carId;
    private String carName;
}