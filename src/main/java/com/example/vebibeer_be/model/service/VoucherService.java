package com.example.vebibeer_be.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Voucher;
import com.example.vebibeer_be.model.repo.VoucherRepo;

@Service
public class VoucherService {
    @Autowired
    VoucherRepo voucherRepo;

    public List<Voucher> getAll(){
        return voucherRepo.findAll();
    }

    public Voucher getById(String voucher_code){
        return voucherRepo.getReferenceById(voucher_code);
    }

    public void save(Voucher voucher){
        voucherRepo.save(voucher);
    }

    public void delete(String voucher_code){
        voucherRepo.deleteById(voucher_code);
    }

}
