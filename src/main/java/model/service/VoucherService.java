package com.example.model.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.entity.Voucher;
import com.example.model.repo.VoucherRepository;




public class VoucherService {
    
    @Autowired
    private VoucherRepository voucherRepository;


     public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucherById(int id) {
        return voucherRepository.findById(id);
    }

   
   
public Voucher updateVoucher(int id, Voucher voucherDetails) {
    Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
    if (optionalVoucher.isPresent()) {
        Voucher existingVoucher = optionalVoucher.get();
        existingVoucher.setVoucher_code(voucherDetails.getVoucher_code());
        existingVoucher.setVoucher_sale(voucherDetails.getVoucher_sale());
        existingVoucher.setStart_time(voucherDetails.getStart_time());
        existingVoucher.setEnd_time(voucherDetails.getEnd_time());
        existingVoucher.setVoucher_condition(voucherDetails.getVoucher_condition());
        return voucherRepository.save(existingVoucher);
    } else {
        throw new RuntimeException("Voucher not found with id " + id);
    }
}

 
    public void deleteVoucher(int id) {
        voucherRepository.deleteById(id);
    }
}
