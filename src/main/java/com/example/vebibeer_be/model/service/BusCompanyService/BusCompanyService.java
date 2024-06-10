package com.example.vebibeer_be.model.service.BusCompanyService;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;



@Service
public class BusCompanyService {

    @Autowired
    private BusCompanyRepo busCompanyRepository;

    // Create a new BusCompany
    public BusCompany createBusCompany(BusCompany busCompany) {
        return busCompanyRepository.save(busCompany);
    }

    // Retrieve all BusCompanies
    public List<BusCompany> getAllBusCompanies() {
        return busCompanyRepository.findAll();
    }

    // Retrieve a BusCompany by ID
    public Optional<BusCompany> getBusCompanyById(int id) {
        return busCompanyRepository.findById(id);
    }

    // Update an existing BusCompany
    @Transactional
    public BusCompany updateBusCompany(int id, BusCompany busCompanyDetails) {
        return busCompanyRepository.findById(id).map(existingBusCompany -> {
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
            return busCompanyRepository.save(existingBusCompany);
        }).orElseThrow(() -> new RuntimeException("BusCompany not found with id " + id));
    }

    // Delete a BusCompany by ID
    public void deleteBusCompany(int id) {
        busCompanyRepository.deleteById(id);
    }
}