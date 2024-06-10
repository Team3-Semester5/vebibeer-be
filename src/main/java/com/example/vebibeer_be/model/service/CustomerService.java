package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Customer;
import com.example.model.repo.CustomerRepository;
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    // create the Customer
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    // get all Customer
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    // get customer by id
    public Optional<Customer> getCustomerById(int id){
        return customerRepository.findById(id);
    }

    // update Customer
    public Customer updateCustomer(int id, Customer customer){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer existCustomer = optionalCustomer.get();
            existCustomer.setCustomer_ava(customer.getCustomer_ava());
            existCustomer.setCustomer_description(customer.getCustomer_description());
            existCustomer.setCustomer_dob(customer.getCustomer_dob());
            existCustomer.setCustomer_fullname(customer.getCustomer_fullname());
            existCustomer.setCustomer_gender(customer.getCustomer_gender());
            existCustomer.setCustomer_nationality(customer.getCustomer_nationality());
            existCustomer.setCustomer_point(customer.getCustomer_point());
            existCustomer.setCustomer_status(customer.getCustomer_status());
            existCustomer.setPassword(customer.getPassword());
            existCustomer.setTypeCustomer(customer.getTypeCustomer());
            existCustomer.setUsername(customer.getUsername());
            return existCustomer;
        }else{
            throw new RuntimeException("Customer was not found by id "+id);
        }
    }

    //delete Customer
    public void deleteCustomer(int id){
        customerRepository.deleteById(id);
    }
}
