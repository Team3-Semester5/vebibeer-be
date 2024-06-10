package com.example.vebibeer_be.model.entities.BusCompany;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int route_id;

    private Date route_startTime;
    private Date route_endTime;
    private String policy;
    private String route_description;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "busCompany_id", referencedColumnName = "busCompany_id")
    private BusCompany busCompany;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "startLocation_id", referencedColumnName = "location_id")
    private Location startLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endLocation_id", referencedColumnName = "location_id")
    private Location endLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "driver_id")
    private Driver driver;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "service_route", joinColumns =  @JoinColumn(name = "route_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services;

 

}