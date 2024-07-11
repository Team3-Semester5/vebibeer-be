package com.example.vebibeer_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.service.report.AdminService;

@RestController
@RequestMapping("/admin-manage")
public class RestAdminController {

        @Autowired
        AdminService adminService = new AdminService();

        @GetMapping("/get-top-5-customer")
        public ResponseEntity<?> getTop5Customer(){
                return ResponseEntity.ok(adminService.getTopCustomer());
        }

        @GetMapping("/get-infor-buscompany")
        public ResponseEntity<?> getRevenueBus(){
                return ResponseEntity.ok(adminService.getAllInforBusCompanyByTime());
        }
        
}
