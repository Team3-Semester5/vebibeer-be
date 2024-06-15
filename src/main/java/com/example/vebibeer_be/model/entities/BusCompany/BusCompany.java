package com.example.vebibeer_be.model.entities.BusCompany;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

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
@ToString
@NoArgsConstructor
@Entity
@Table(name = "buscompany")
public class BusCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busCompany_id;

    private String username;
    private String password;
    private String busCompany_status;
    private String busCompany_fullname;
    private String busCompany_dob;
    private String busCompany_imgUrl;
    private String busCompany_description;
    private String busCompany_nationally;
    private String busCompany_name;
    private String busCompany_contract;
    private String busCompany_location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;
}