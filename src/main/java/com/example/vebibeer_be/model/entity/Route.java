package com.example.model.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int route_id;

    @ManyToOne
    private BusCompany busCompany;

    @OneToOne
    private Location start_point;

    @OneToOne
    private Location end_point;

    private Date start_time;
    private Date end_time;
    private String route_policy;
    private String route_description;

    @OneToOne
    private Car car;

    @OneToOne
    private Driver driver;
}
