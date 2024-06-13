package com.example.vebibeer_be.model.entities.Customer;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "typeCustomer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TypeCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeCustomer_id;

    private String typeCustomer_name;
    private String typeCustomer_description;

    @OneToMany(mappedBy = "typeCustomer")
    @JsonBackReference
    private Set<Customer> customers;
}
