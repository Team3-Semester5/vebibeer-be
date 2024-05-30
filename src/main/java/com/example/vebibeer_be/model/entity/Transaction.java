package com.example.model.entity;

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
public class Transaction {
    private int transaction_id;
    private Ticket ticket;
    private Customer customer;
    private Double transaction_VAT;
    private int transaction_point;
    private String transaction_status;
    private Date transaction_time_to_edit;
    private Voucher voucher;
    private PaymentMethod paymentMethod;
}
