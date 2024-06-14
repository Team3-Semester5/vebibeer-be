package com.example.vebibeer_be.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;
import com.example.vebibeer_be.model.service.CloudinaryService;

@RestController
@RequestMapping(value = "/customer")
public class RestCustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Customer>> showList() {
        List<Customer> customerList = customerService.getAllCustomer();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> showDetailCustomer(@PathVariable("id") int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = {"/delete/{id}/", "/delete/{id}"})
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") int customer_id) {
        Customer customer = customerService.getCustomerById(customer_id);
        if (customer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(customer_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<Customer> uploadCustomerImage(
            @PathVariable("id") int customerId,
            @RequestParam("file") MultipartFile file) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Upload ảnh lên Cloudinary
            String imageUrl = cloudinaryService.uploadFile(file);

            // Cập nhật đường dẫn ảnh vào đối tượng Customer
            customer.setCustomer_img_ava(imageUrl);

            // Lưu thông tin khách hàng vào cơ sở dữ liệu
            customerService.saveCustomer(customer);

            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
