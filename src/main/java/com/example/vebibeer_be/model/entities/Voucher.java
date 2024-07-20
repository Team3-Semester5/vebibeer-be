package com.example.vebibeer_be.model.entities;

import java.sql.Timestamp;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "voucher")
public class Voucher {
    @Id
    private String voucher_code;

    private double saleUp;
    private Timestamp startTime;
    private Timestamp endTime;
    private String voucher_condition;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "busCompany_id", referencedColumnName = "busCompany_id") // đảm bảo tên cột đúng
    @JsonBackReference
    private BusCompany busCompany;
}