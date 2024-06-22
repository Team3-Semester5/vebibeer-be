package com.example.vebibeer_be.model.service.CustomerService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.entities.Customer.TypeCustomer;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.TypeCustomerRepo;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TypeCustomerRepo typeCustomerRepo;

    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public Customer getCustomerById(int customer_id){
        return customerRepo.getReferenceById(customer_id);
    }

    public void saveCustomer(Customer customer){
        customerRepo.save(customer);
    }

    public void deleteCustomer(int customer_id){
        customerRepo.deleteById(customer_id);
    }

    public List<TypeCustomer> getAllTypeCustomer(){
        return typeCustomerRepo.findAll();
    }
    

    // public Customer findByUsername(String username) {
    //     return customerRepo.findByUsername(username);
    // }

    public Customer findById(int id) {
        return customerRepo.findById(id).orElse(null);
    }


    public Customer findByUsername(String username) {
        logger.info("Finding customer by username: {}", username);
        Customer customer = customerRepo.findByUsername(username);
        if (customer == null) {
            logger.warn("Customer not found with username: {}", username);
        } else {
            logger.info("Customer found: {}", customer);
        }
        return customer;
    }

}
