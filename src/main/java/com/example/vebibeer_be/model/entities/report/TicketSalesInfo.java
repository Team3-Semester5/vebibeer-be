package com.example.vebibeer_be.model.entities.report;

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
public class TicketSalesInfo {
    private int busCompany_id;
    private String busCompany_name;
    private String info_time;
    private long amount_tickets;
}
