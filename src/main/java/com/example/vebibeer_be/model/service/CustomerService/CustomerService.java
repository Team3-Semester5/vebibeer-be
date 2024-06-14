package com.example.vebibeer_be.model.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.entities.Customer.TypeCustomer;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.TypeCustomerRepo;

@Service
public class CustomerService {
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

}
