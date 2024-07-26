package com.example.vebibeer_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketLockRequest {
    private String ticketSeat;
    private String customerName;

    // Getters and Setters
}