package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.example.model.entity.TypeCustomer;
import  com.example.model.repo.TypeCustomerRepository;


@Service
public class TypeCustomerService {
    @Autowired
    TypeCustomerRepository typeCustomerRepository;
    // create the typecus
    public TypeCustomer creatTypeCustomerService(TypeCustomer typeCustomer){
        return typeCustomerRepository.save(typeCustomer);
    }
    // get all typecus
    public List<TypeCustomer> getAllTypeCustomers(){
        return typeCustomerRepository.findAll();
    }
     // get typecus by id
    public Optional<TypeCustomer> getTypeCustomerById(int id){
        return typeCustomerRepository.findById(id);
    }
   

    // update type cus
    public TypeCustomer updaTypeCustomer(int id, TypeCustomer typeCustomer){
        Optional<TypeCustomer> optionalTypeCustomer = typeCustomerRepository.findById(id);
        if(optionalTypeCustomer.isPresent()){
            TypeCustomer existTypeCustomer = optionalTypeCustomer.get();
            existTypeCustomer.setTypeCustomer_description(typeCustomer.getTypeCustomer_description());
            existTypeCustomer.setTypeCustomer_name(typeCustomer.getTypeCustomer_name());
            return existTypeCustomer;
        }else{
            throw new RuntimeException("TypeCustomer was not found with ID "+id);
        }
    }
    //delete
    public void deleteTypeCustomer(int id){
        typeCustomerRepository.deleteById(id);
    }
}
