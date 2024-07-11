package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.report.RevenueOfYear;
import com.example.vebibeer_be.model.entities.report.TicketSalesInfo;
import com.example.vebibeer_be.model.service.report.BusManageService;
import com.example.vebibeer_be.model.service.report.RevenueReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = { "/manageBus" })
public class RestBusManageController {
    @Autowired
    BusManageService busManageService;

    // month
    @GetMapping("{idBus}")
    public ResponseEntity<?> getMethodName(@PathVariable("idBus") int busCompany_id,
            @RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
        List<TicketSalesInfo> hello = busManageService.findTicketSalesInfoForMonth(busCompany_id, year, month);
        return ResponseEntity.ok(hello);
    }

    // year
    @GetMapping("{idBus}/yearly-sales")
    public ResponseEntity<List<TicketSalesInfo>> getYearlyTicketSales(@PathVariable("idBus") int busCompanyId,
            @RequestParam("year") int year) {
        List<TicketSalesInfo> ticketSalesInfo = busManageService.findTicketSalesInfoForYear(busCompanyId, year);
        return ResponseEntity.ok(ticketSalesInfo);
    }

    @GetMapping("/top-companies")
    public ResponseEntity<List<TicketSalesInfo>> getTopBusCompaniesByTicketSales(@RequestParam("year") int year) {
        List<TicketSalesInfo> ticketSalesInfos = busManageService.findTopBusCompaniesByTicketSales(year);
        return ResponseEntity.ok(ticketSalesInfos);
    }

    @Autowired
    private RevenueReportService revenueReportService;

    @GetMapping("/revenue/{busCompanyId}")
    public ResponseEntity<List<RevenueOfYear>> getAnnualRevenue(@PathVariable("busCompanyId") int busCompanyId,
            @RequestParam(name = "year") int year) {

        List<RevenueOfYear> revenueList = revenueReportService.findAnnualRevenue(busCompanyId, year);

        return ResponseEntity.ok(revenueList);

    }

    @GetMapping("/revenue/top-companies")
    public ResponseEntity<List<RevenueOfYear>> getTopCompaniesByRevenue(@RequestParam(name = "year") int year) {
        List<RevenueOfYear> topCompaniesList = revenueReportService.findTopCompaniesByRevenue(year);
        return ResponseEntity.ok(topCompaniesList);
    }

    @GetMapping("/revenue/{busCompanyId}/monthly")
    public ResponseEntity<List<RevenueOfYear>> getMonthlyRevenueByCompanyAndMonth(
            @PathVariable int busCompanyId,
            @RequestParam int year,
            @RequestParam int month) {

        List<RevenueOfYear> monthlyRevenue = revenueReportService.findMonthlyRevenueByCompanyAndMonth(busCompanyId,
                year, month);
        return ResponseEntity.ok(monthlyRevenue);
    }
}