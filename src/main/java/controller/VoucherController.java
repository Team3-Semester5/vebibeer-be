package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.entity.Voucher;
import com.example.model.service.VoucherService;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
    
    @Autowired
    private VoucherService voucherService;

    @PostMapping
    public Voucher createVoucher(@RequestBody Voucher voucher) {
        return voucherService.createVoucher(voucher);
    }

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable int id) {
        Optional<Voucher> voucher = voucherService.getVoucherById(id);
        if (voucher.isPresent()) {
            return ResponseEntity.ok(voucher.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable int id, @RequestBody Voucher voucherDetails) {
        try {
            Voucher updatedVoucher = voucherService.updateVoucher(id, voucherDetails);
            return ResponseEntity.ok(updatedVoucher);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable int id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }
}
