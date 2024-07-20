package com.example.vebibeer_be.model.entities.BusCompany;

import org.antlr.v4.runtime.misc.NotNull;

import com.example.vebibeer_be.model.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "buscompany")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusCompany extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busCompany_id;

    private String busCompany_status;
    private String busCompany_fullname;
    private String busCompany_dob;
    private String busCompany_imgUrl;
    private String busCompany_description;
    private String busCompany_nationally;
    private String busCompany_name;
    private String busCompany_contract;
    private String busCompany_location;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @JsonBackReference
    private Location location;

}
