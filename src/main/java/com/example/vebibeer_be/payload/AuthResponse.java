package com.example.vebibeer_be.payload;

import com.example.vebibeer_be.model.entities.User;
import com.example.vebibeer_be.model.entities.Customer.Customer;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User customer;

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AuthResponse(String accessToken, User customer) {
        this.accessToken = accessToken;
        this.customer = customer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
