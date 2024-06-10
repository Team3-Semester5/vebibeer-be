package com.example.model.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString
@NoArgsConstructor
@Entity
@Table(name = "BusCompany")
public class BusCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buscompany_id;
    private String username;
    private String password;
    private String buscompany_status;
    private String buscompany_fullname;
    private Date buscompany_dob;
    private String buscompany_ava;
    private String buscompany_description;
    private String buscompany_nationality;
    private String buscompany_name;
    private String buscompany_location;
    private String buscompany_contract;
    @OneToMany(mappedBy = "busCompany", cascade = CascadeType.ALL)
    private List<Car> cars;

    @OneToMany(mappedBy = "busCompany", cascade = CascadeType.ALL)
    private List<Route> routes;
}
