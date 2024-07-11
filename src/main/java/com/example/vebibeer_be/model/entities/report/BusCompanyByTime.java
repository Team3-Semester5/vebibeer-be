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
public class BusCompanyByTime {
    private String bus_company_name;
    private int year;
    private int month;
    private long tickets_sold;
}
