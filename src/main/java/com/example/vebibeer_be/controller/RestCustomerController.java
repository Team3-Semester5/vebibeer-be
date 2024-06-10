package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/customer")
public class RestCustomerController {
    @Autowired
    CustomerService customerService = new CustomerService();

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Customer>> showList() {
        List<Customer> customerList = customerService.getAllCustomer();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
    }

    @GetMapping(value = { "/{id}", "/{id}/" })
    public ResponseEntity<Customer> showDetailCustomer(HttpServletRequest request,
            @PathVariable(name = "id") int customer_id) {
        HttpSession session = request.getSession();
        Customer currentUser = new Customer();
        try {
            currentUser = (Customer) session.getAttribute("currentUser");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (customerService.getCustomerById(customer_id) == null) {
            return new ResponseEntity<Customer>(currentUser, HttpStatus.NOT_FOUND);
        }
        if (currentUser.getCustomer_id() == customer_id) {
            return new ResponseEntity<Customer>(currentUser, HttpStatus.OK);
        }
        return new ResponseEntity<Customer>(currentUser, HttpStatus.FOUND);
    }

    @GetMapping(value = { "/delete/{id}/", "/delete/{id}" })
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") int customer_id) {
        Customer customer = customerService.getCustomerById(customer_id);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(customer_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<Customer> postMethodName(@RequestBody Customer customer) {
        System.out.println(customer.toString());
        customerService.saveCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
