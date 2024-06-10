package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.entity.Customer;
import com.example.model.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("")
    public List<Customer> getAllCustomers(@RequestParam Customer customer) {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@RequestParam int id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if(optionalCustomer.isPresent()){
            return ResponseEntity.ok(optionalCustomer.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> putMethodName(@PathVariable int id, @RequestBody Customer customer) {
        try {
            Customer updaCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updaCustomer);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")    
    public ResponseEntity<Void> deleteBusCompany(@PathVariable int id) {
        customerService.deleteCustomer(id);;
        return ResponseEntity.noContent().build();
    }
}
