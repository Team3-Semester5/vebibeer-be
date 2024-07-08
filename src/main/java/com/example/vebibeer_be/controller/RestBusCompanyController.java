package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.vebibeer_be.model.entities.Rating;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.service.CloudinaryService;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping(value = "/admin/buscompanies")
public class RestBusCompanyController {
    @Autowired
    BusCompanyService busCompanyService = new BusCompanyService();

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<BusCompany>> showList() {
        List<BusCompany> busCompanies = busCompanyService.getAll();
        if (busCompanies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<BusCompany>>(busCompanies, HttpStatus.OK);
    }

    @GetMapping(value = { "/{id}", "/{id}/" })
    public ResponseEntity<BusCompany> showDetail(@PathVariable(name = "id") int busCompany_id) {
        BusCompany busCompany = busCompanyService.getById(busCompany_id);
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

    @PostMapping(value = { "/save", "/save/" }, consumes = "application/json;charset=UTF-8")
    public ResponseEntity<BusCompany> add(@RequestBody BusCompany busCompany) {
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        busCompanyService.save(busCompany);
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<BusCompany> delete(@PathVariable(name = "id") int busCompany_id) {
        BusCompany busCompany = busCompanyService.getById(busCompany_id);
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        busCompanyService.delete(busCompany_id);
        return new ResponseEntity<BusCompany>(busCompany, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    // @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<BusCompany> updateBusCompany(@PathVariable(name = "id") int id,
            @RequestBody BusCompany busCompanyDetails) {
        try {
            BusCompany updatedBusCompany = busCompanyService.updateBusCompany(id, busCompanyDetails);
            return ResponseEntity.ok(updatedBusCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload/{id}") // Thêm endpoint xử lý tải lên ảnh
    public ResponseEntity<BusCompany> uploadBusCompanyImage(
            @PathVariable("id") int busCompanyId,
            @RequestParam("file") MultipartFile file) throws IOException, java.io.IOException {
        BusCompany busCompany = busCompanyService.getById(busCompanyId);
        if (busCompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Upload ảnh lên Cloudinary
        String imageUrl = cloudinaryService.uploadFile(file);

        // Cập nhật đường dẫn ảnh vào đối tượng BusCompany
        busCompany.setBusCompany_imgUrl(imageUrl);

        // Lưu thông tin công ty xe buýt vào cơ sở dữ liệu
        busCompanyService.save(busCompany);

        return new ResponseEntity<>(busCompany, HttpStatus.OK);
    }
}