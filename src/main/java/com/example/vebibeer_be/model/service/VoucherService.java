package com.example.vebibeer_be.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.Voucher;
import com.example.vebibeer_be.model.repo.VoucherRepo;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepo voucherRepo;

    public List<Voucher> getAll() {
        return voucherRepo.findAll();
    }

    public Voucher getById(String voucherCode) {
        return voucherRepo.findById(voucherCode).orElse(null);
    }

    public Voucher save(Voucher voucher) {
        return voucherRepo.save(voucher);
    }

    public void delete(String voucherCode) {
        voucherRepo.deleteById(voucherCode);
    }

    public List<Voucher> getByBusCompanyId(int busCompanyId) {
        return voucherRepo.findByBusCompanyId(busCompanyId);
    }

    public Voucher update(String voucherCode, Voucher updatedVoucher) {
        return voucherRepo.findById(voucherCode)
            .map(voucher -> {
                voucher.setSaleUp(updatedVoucher.getSaleUp());
                voucher.setStartTime(updatedVoucher.getStartTime());
                voucher.setEndTime(updatedVoucher.getEndTime());
                voucher.setVoucher_condition(updatedVoucher.getVoucher_condition());
                return voucherRepo.save(voucher);
            }).orElseThrow(() -> new RuntimeException("Voucher not found!"));
    }
}