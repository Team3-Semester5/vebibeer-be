package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.BusCompany.Service;
import com.example.vebibeer_be.model.service.BusCompanyService.ServicesService;

@RestController
@RequestMapping("/admin/service")
public class RestServiceController {
    @Autowired
    ServicesService servicesService = new ServicesService();

     @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Service>> showList() {
        List<Service> services = servicesService.getAll();
        if (services.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Service>>(services, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Service> save(@RequestBody Service newService) {
        if (newService == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Service Service = servicesService.getById(newService.getService_id());
        if (Service == null) {
            servicesService.save(Service);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        servicesService.save(Service);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Service> getById(@PathVariable(name = "id")int service_id) {
        Service Service = servicesService.getById(service_id);
        if (Service == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Service>(Service, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Service> delete(@PathVariable(name = "id") int service_id){
        Service Service = servicesService.getById(service_id);
        if (Service == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        servicesService.delete(service_id);
        return new ResponseEntity<Service>(Service, HttpStatus.OK);
    }

}
