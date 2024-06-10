package com.example.vebibeer_be.model.entities;

import java.sql.Date;
import java.util.Set;

import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.entities.Customer.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;

    private double transaction_vat;
    private int transaction_point;
    private String transaction_status;
    private Date transaction_timeEdit;

    @ManyToMany
    @JoinTable(name = "ticket_transaction", joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private Set<Ticket> tickets;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_code", referencedColumnName = "voucher_code")
    private Voucher voucher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentMethod_id", referencedColumnName = "paymentMethod_id")
    private PaymentMethod paymentMethod;

}
