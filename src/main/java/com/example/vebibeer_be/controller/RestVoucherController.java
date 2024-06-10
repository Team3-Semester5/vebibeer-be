package com.example.vebibeer_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vebibeer_be.model.entities.Voucher;
import com.example.vebibeer_be.model.service.VoucherService;

@RestController
@RequestMapping("/voucher")
public class RestVoucherController {
    @Autowired
    VoucherService voucherService = new VoucherService();

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Voucher>> showList() {
        List<Voucher> Vouchers = voucherService.getAll();
        if (Vouchers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Voucher>>(Vouchers, HttpStatus.OK);
    }

    @PostMapping(value = { "/save", "/save/" })
    public ResponseEntity<Voucher> save(@RequestBody Voucher newVoucher) {
        if (newVoucher == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Voucher Voucher = voucherService.getById(newVoucher.getVoucher_code());
        if (Voucher == null) {
            voucherService.save(Voucher);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        voucherService.save(Voucher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = { "/{id}", "/{id}/" })
    public ResponseEntity<Voucher> getById(@PathVariable(name = "id") String Voucher_code) {
        Voucher Voucher = voucherService.getById(Voucher_code);
        if (Voucher == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Voucher>(Voucher, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public ResponseEntity<Voucher> delete(@PathVariable(name = "id") String Voucher_code) {
        Voucher Voucher = voucherService.getById(Voucher_code);
        if (Voucher == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        voucherService.delete(Voucher_code);
        return new ResponseEntity<Voucher>(Voucher, HttpStatus.OK);
    }
}
