package com.example.vebibeer_be.model.entities.Customer;

import java.time.LocalDate;
import java.util.Set;

import com.example.vebibeer_be.model.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    private String username;
    private String password;
    private String customer_status;
    private String customer_fullname;
    private LocalDate customer_dob;
    private String customer_img_ava;
    private String customer_nationality;
    private String customer_gender;
    private String customer_description;
    private boolean verify_purchased;
    private long point;

    @ManyToOne
    @JoinColumn(name = "typeCustomer_Id", referencedColumnName = "typeCustomer_id")
    private TypeCustomer typeCustomer;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Transaction> transactions;

}