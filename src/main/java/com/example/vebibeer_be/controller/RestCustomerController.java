package com.example.vebibeer_be.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.vebibeer_be.dto.PasswordChangeDTO;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.CloudinaryService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") int customerId, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        // Log the received payload for debugging
        System.out.println("Received change password request for customer ID: " + customerId);
        System.out.println("Old Password: " + passwordChangeDTO.getOldPassword());
        System.out.println("New Password: " + passwordChangeDTO.getNewPassword());
        System.out.println("Confirm Password: " + passwordChangeDTO.getConfirmPassword());
    
        if (passwordChangeDTO.getOldPassword() == null || 
            passwordChangeDTO.getNewPassword() == null || 
            passwordChangeDTO.getConfirmPassword() == null) {
            return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
        }
    
        Customer existingCustomer = customerService.getCustomerById(customerId);
        if (existingCustomer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    
        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmPassword())) {
            return new ResponseEntity<>("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
        }
      
        if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), existingCustomer.getPassword())) {
            return new ResponseEntity<>("Old password is incorrect", HttpStatus.UNAUTHORIZED);
        }
    
        existingCustomer.setPassword(passwordChangeDTO.getNewPassword());
        customerService.saveCustomer(existingCustomer);
    
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    @PostMapping("/updateProfile/{id}")
    public ResponseEntity<Customer> updateProfile(@PathVariable("id") int customerId, @RequestBody Customer updatedCustomer) {
        Customer existingCustomer = customerService.getCustomerById(customerId);
        if (existingCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        existingCustomer.setCustomer_fullname(updatedCustomer.getCustomer_fullname());
        existingCustomer.setCustomer_gender(updatedCustomer.getCustomer_gender());
        existingCustomer.setCustomer_nationality(updatedCustomer.getCustomer_nationality());
        existingCustomer.setCustomer_dob(updatedCustomer.getCustomer_dob());
    
        customerService.saveCustomerWithoutChangePass(existingCustomer);
    
        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }


}
