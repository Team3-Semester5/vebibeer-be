package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;

import jakarta.transaction.Transactional;

@Service
public class BusCompanyService {
    @Autowired
    BusCompanyRepo busCompanyRepo;

    public List<BusCompany> getAll(){
        return busCompanyRepo.findAll();
    }

    public BusCompany getById(int busCompany_id){
        return busCompanyRepo.getReferenceById(busCompany_id);
    }

    public void save(BusCompany busCompany){
        busCompanyRepo.save(busCompany);
    }

    public void delete(int busCompany_id){
        busCompanyRepo.deleteById(busCompany_id);
    }
    
    @Transactional
    public BusCompany updateBusCompany(int id, BusCompany busCompanyDetails) {
        return busCompanyRepo.findById(id).map(existingBusCompany -> {
            existingBusCompany.setUserId(null);
            existingBusCompany.setUsername(busCompanyDetails.getUsername());
            existingBusCompany.setPassword(busCompanyDetails.getPassword());
            existingBusCompany.setBusCompany_status(busCompanyDetails.getBusCompany_status());  
            existingBusCompany.setBusCompany_fullname(busCompanyDetails.getBusCompany_fullname());
            existingBusCompany.setBusCompany_dob(busCompanyDetails.getBusCompany_dob());
            existingBusCompany.setBusCompany_imgUrl(busCompanyDetails.getBusCompany_imgUrl());
            existingBusCompany.setBusCompany_description(busCompanyDetails.getBusCompany_description());
            existingBusCompany.setBusCompany_nationally(busCompanyDetails.getBusCompany_nationally());
            existingBusCompany.setBusCompany_name(busCompanyDetails.getBusCompany_name());
            existingBusCompany.setBusCompany_location(busCompanyDetails.getBusCompany_location());
            existingBusCompany.setBusCompany_contract(busCompanyDetails.getBusCompany_contract());
            return busCompanyRepo.save(existingBusCompany);
        }).orElseThrow(() -> new RuntimeException("BusCompany not found with id " + id));
    }

}
