package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.report.TicketSalesInfo;
import com.example.vebibeer_be.model.service.report.BusManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = {"/manageBus"})
public class RestBusManageController {
    @Autowired
    BusManageService busManageService;

    @GetMapping("{idBus}")
    public ResponseEntity<?> getMethodName(@PathVariable("idBus") int busCompany_id, @RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
        List<TicketSalesInfo> hello = busManageService.findTicketSalesInfoForMonth(busCompany_id, year, month);
        return ResponseEntity.ok(hello);
    }
    

}
