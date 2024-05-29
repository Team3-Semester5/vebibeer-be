package com.example.vebibeer_be.model.entity;

import java.sql.Date;

import java.util.Set;

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
    @JoinColumn(name = "buscompany_id", referencedColumnName = "buscompany_id")
    private BusCompany busCompany;

    @ManyToOne
    @JoinColumn(name = "start_location_id", referencedColumnName = "location_id")
    private Location start_point;

    @ManyToOne
    @JoinColumn(name = "end_location_id", referencedColumnName = "location_id")
    private Location end_point;

    private Date start_time;
    private Date end_time;
    private String route_policy;
    private String route_description;

    @ManyToMany
    @JoinTable(name = "Route_car", joinColumns = @JoinColumn(name = "route_id"), inverseJoinColumns = @JoinColumn(name = "car_id"))
    private Set<Car> car;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "driver_id")
    private Driver driver;
}