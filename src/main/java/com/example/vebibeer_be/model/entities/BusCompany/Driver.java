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
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driver_id;

    private String driver_name;
    private String driver_avaUrl;
    private String driver_description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "busCompany_id", referencedColumnName = "busCompany_id")
    private BusCompany busCompany;

}
