package com.example.vebibeer_be.model.entities.BusCompany;

import jakarta.persistence.CascadeType;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int car_id;

    private String car_code;
    private int amount_seat;
    private String car_imgUrl1;
    private String car_imgUrl2;
    private String car_imgUrl3;
    private String car_imgUrl4;
    private String car_imgUrl5;
    private String car_imgUrl6;
    private String car_manufacturer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "busCompany_id", referencedColumnName = "busCompany_id")
    private BusCompany busCompany;

}
