package com.example.vebibeer_be.model.entity;

import java.sql.Date;

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
public class Voucher {
    private String voucher_code;
    private Double voucher_sale;
    private Date start_time;
    private Date end_time;
    private String voucher_condition;
}
