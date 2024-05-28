package com.example.vebibeer_be.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entity.BusCompany;
import com.example.vebibeer_be.model.repo.BusCompanyRepository;

@Service
public class BusCompanyService {

    @Autowired
    private BusCompanyRepository busCompanyRepository;

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
    public BusCompany updateBusCompany(int id, BusCompany busCompanyDetails) {
        Optional<BusCompany> optionalBusCompany = busCompanyRepository.findById(id);
        if (optionalBusCompany.isPresent()) {
            BusCompany existingBusCompany = optionalBusCompany.get();
            existingBusCompany.setUsername(busCompanyDetails.getUsername());
            existingBusCompany.setPassword(busCompanyDetails.getPassword());
            existingBusCompany.setBuscompany_status(busCompanyDetails.getBuscompany_status());
            existingBusCompany.setBuscompany_fullname(busCompanyDetails.getBuscompany_fullname());
            existingBusCompany.setBuscompany_dob(busCompanyDetails.getBuscompany_dob());
            existingBusCompany.setBuscompany_ava(busCompanyDetails.getBuscompany_ava());
            existingBusCompany.setBuscompany_description(busCompanyDetails.getBuscompany_description());
            existingBusCompany.setBuscompany_nationality(busCompanyDetails.getBuscompany_nationality());
            existingBusCompany.setBuscompany_name(busCompanyDetails.getBuscompany_name());
            existingBusCompany.setBuscompany_location(busCompanyDetails.getBuscompany_location());
            existingBusCompany.setBuscompany_contract(busCompanyDetails.getBuscompany_contract());
            return busCompanyRepository.save(existingBusCompany);
        } else {
            throw new RuntimeException("BusCompany not found with id " + id);
        }
    }

    // Delete a BusCompany by ID
    public void deleteBusCompany(int id) {
        busCompanyRepository.deleteById(id);
    }
}