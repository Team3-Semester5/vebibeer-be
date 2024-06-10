package com.example.vebibeer_be.model.service.BusCompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.repo.BusCompanyRepo.ServiceRepo;

@Service
public class ServicesService {
    @Autowired
    ServiceRepo serviceRepo;

    public List<com.example.vebibeer_be.model.entities.BusCompany.Service> getAll(){
        return serviceRepo.findAll();
    }

    public com.example.vebibeer_be.model.entities.BusCompany.Service getById(int service_id){
        return serviceRepo.getReferenceById(service_id);
    }

    public void save(com.example.vebibeer_be.model.entities.BusCompany.Service service){
        serviceRepo.save(service);
    }

    public void delete(int service_id){
        serviceRepo.deleteById(service_id);
    }

}
