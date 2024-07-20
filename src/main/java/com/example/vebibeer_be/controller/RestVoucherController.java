package com.example.vebibeer_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.vebibeer_be.model.entities.Voucher;
import com.example.vebibeer_be.model.service.VoucherService;
import java.util.List;

@RestController
@RequestMapping("/buscompany/voucher")
public class RestVoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping ({"" ,"/"})
    public ResponseEntity<List<Voucher>> showList() {
        List<Voucher> vouchers = voucherService.getAll();
        return vouchers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vouchers);
    }

    @PostMapping("/save")
    public ResponseEntity<Voucher> save(@RequestBody Voucher newVoucher) {
        if (newVoucher == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(voucherService.save(newVoucher));
    }

    @PutMapping("/{voucherCode}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable String voucherCode, @RequestBody Voucher updatedVoucher) {
        try {
            Voucher savedVoucher = voucherService.update(voucherCode, updatedVoucher);
            return ResponseEntity.ok(savedVoucher);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Voucher voucher = voucherService.getById(id);
        return voucher != null ? ResponseEntity.ok(voucher) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (voucherService.getById(id) != null) {
            voucherService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/bus/{busCompanyId}")
    public ResponseEntity<List<Voucher>> getByBusCompanyId(@PathVariable int busCompanyId) {
        List<Voucher> vouchers = voucherService.getByBusCompanyId(busCompanyId);
        return vouchers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vouchers);
    }
}