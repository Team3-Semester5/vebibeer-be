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

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;

@RestController
@RequestMapping(value = "/admin/buscompanies")
public class RestBusCompanyController {
    @Autowired
    BusCompanyService busCompanyService = new BusCompanyService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<BusCompany>> showList(){
        List<BusCompany> busCompanies = busCompanyService.getAll();
        if (busCompanies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<BusCompany>>(busCompanies, HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<BusCompany> showDetail(@PathVariable(name = "id") int busCompany_id){
        BusCompany busCompany = busCompanyService.getById(busCompany_id);
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

    @PostMapping(value = {"/save", "/save/"}, consumes = "application/json;charset=UTF-8")
    public ResponseEntity<BusCompany> add(@RequestBody BusCompany busCompany){
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        busCompanyService.save(busCompany);
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<BusCompany> delete(@PathVariable(name = "id") int busCompany_id){
        BusCompany busCompany = busCompanyService.getById(busCompany_id);
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        busCompanyService.delete(busCompany_id);
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

}
