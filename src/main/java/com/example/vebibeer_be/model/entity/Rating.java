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
public class Rating {
    private int rating_id;
    private int amount_star;
    private String content;
    private Date time_to_edit;
    private BusCompany busCompany;
    private Customer customer;
}
