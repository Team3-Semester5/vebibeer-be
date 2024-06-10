package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;

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

}
