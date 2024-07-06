package com.example.vebibeer_be.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.entities.Customer.Customer;

public class OrderRequest {
    private int amount;
    private String orderInfo;

    // Getters and setters
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

}
