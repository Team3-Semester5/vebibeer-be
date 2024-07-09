package com.example.vebibeer_be.model.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
