package com.example.vebibeer_be.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int car_id;
    private String car_code;
    private int car_amount_seat;
    private String car_img1;
    private String car_img2;
    private String car_img3;
    private String car_img4;
    private String car_img5;
    private String car_img6;
    private String car_manufacturer;

    @ManyToOne
    @JoinColumn(name = "buscompany_id", referencedColumnName = "buscompany_id")
    private BusCompany busCompany;
}
