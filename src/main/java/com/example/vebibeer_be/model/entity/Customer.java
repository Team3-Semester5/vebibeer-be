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

public class Customer {
    private int customer_id;
    private String username;
    private String password;
    private String customer_status;
    private String customer_fullname;
    private Date customer_dob;
    private String customer_ava;
    private String customer_description;
    private String customer_nationality;
    private String customer_gender;
    private boolean verify_purchased;
    private int customer_point;
    private TypeCustomer typeCustomer;
}
