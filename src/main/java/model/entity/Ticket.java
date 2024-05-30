package com.example.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    private int ticket_id;
    private Route route;
    private Double ticket_price;
    private int ticket_seat;
    private String ticket_status;
}

