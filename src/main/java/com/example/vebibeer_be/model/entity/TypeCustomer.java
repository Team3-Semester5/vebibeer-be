package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class TypeCustomer {
    private int typeCustomer_id;
    private String typeCustomer_name;
    private String typeCustomer_description;
    
}