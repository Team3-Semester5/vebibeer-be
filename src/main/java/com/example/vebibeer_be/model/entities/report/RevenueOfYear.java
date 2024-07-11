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
public class RevenueOfYear {
    private int busCompany_id;
    private String busCompany_name;
    private String transaction_year;
    private double total_earnings; // Assuming earnings could be with decimals
}