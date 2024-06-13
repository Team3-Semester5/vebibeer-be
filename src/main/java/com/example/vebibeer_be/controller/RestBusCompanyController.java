package com.example.vebibeer_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/buscompanies")
public class RestBusCompanyController {

    @Autowired
    private BusCompanyService busCompanyService;

    @PostMapping("/")
    public ResponseEntity<BusCompany> createBusCompany(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("busCompany_status") String busCompanyStatus,
            @RequestParam("busCompany_fullname") String busCompanyFullname,
            @RequestParam("busCompany_dob") String busCompanyDob,
            @RequestParam(value = "busCompany_imgUrl", required = false) MultipartFile busCompanyImgUrl,
            @RequestParam("busCompany_description") String busCompanyDescription,
            @RequestParam("busCompany_nationally") String busCompanyNationally,
            @RequestParam("busCompany_name") String busCompanyName,
            @RequestParam("busCompany_location") String busCompanyLocation,
            @RequestParam("busCompany_contract") String busCompanyContract) {

        try {
            // Handle the file upload
            String imgUrl = null;
            if (busCompanyImgUrl != null && !busCompanyImgUrl.isEmpty()) {
                // You can save the file to disk or a cloud storage here
                imgUrl = busCompanyImgUrl.getOriginalFilename();
                // Save the file using your preferred method
            }

            BusCompany busCompany = new BusCompany();
            busCompany.setUsername(username);
            busCompany.setPassword(password);
            busCompany.setBusCompany_status(busCompanyStatus);
            busCompany.setBusCompany_fullname(busCompanyFullname);
            busCompany.setBusCompany_dob(busCompanyDob);
            busCompany.setBusCompany_imgUrl(imgUrl);
            busCompany.setBusCompany_description(busCompanyDescription);
            busCompany.setBusCompany_nationally(busCompanyNationally);
            busCompany.setBusCompany_name(busCompanyName);
            busCompany.setBusCompany_location(busCompanyLocation);
            busCompany.setBusCompany_contract(busCompanyContract);

            BusCompany createdBusCompany = busCompanyService.createBusCompany(busCompany);
            return ResponseEntity.ok(createdBusCompany);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<BusCompany>> getAllBusCompanies() {
        List<BusCompany> busCompanies = busCompanyService.getAllBusCompanies();
        return ResponseEntity.ok(busCompanies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusCompany> getBusCompanyById(@PathVariable int id) {
        return busCompanyService.getBusCompanyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusCompany> updateBusCompany(@PathVariable int id, @RequestBody BusCompany busCompanyDetails) {
        try {
            BusCompany updatedBusCompany = busCompanyService.updateBusCompany(id, busCompanyDetails);
            return ResponseEntity.ok(updatedBusCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusCompany(@PathVariable int id) {
        try {
            busCompanyService.deleteBusCompany(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}