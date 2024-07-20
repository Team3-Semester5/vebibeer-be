package com.example.vebibeer_be.model.entities.report;

import java.math.BigDecimal;

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
    private BigDecimal total_revenue;
    private long tickets_sold;
}
