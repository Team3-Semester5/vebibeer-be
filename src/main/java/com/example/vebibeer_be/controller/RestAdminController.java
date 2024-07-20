package com.example.vebibeer_be.controller;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.report.BusCompanyByTime;
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
        public ResponseEntity<?> getAllBusByRevenue(@RequestParam(name = "year") int year){
                return ResponseEntity.ok(adminService.getAllInforBusCompanyByTime(year));
        }

        @GetMapping("/get-top-5-buscompany")
        public ResponseEntity<?> getTop5BusByRevenue(@RequestParam(name = "year") int year){
                ArrayList<BusCompanyByTime> busCompanyByTimeList = new ArrayList<>();
                ArrayList<BusCompanyByTime> all = adminService.getAllInforBusCompanyByTime(year);
                for (int i = 0; i < 5; i++) {
                        if (i >= all.size()) {
                                break;
                        }
                        busCompanyByTimeList.add(all.get(i));
                }
                busCompanyByTimeList.sort(Comparator.comparing(BusCompanyByTime::getTotal_revenue).reversed());
                return ResponseEntity.ok(busCompanyByTimeList);
        }
        
}