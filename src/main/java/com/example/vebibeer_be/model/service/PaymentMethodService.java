package com.example.vebibeer_be.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.PaymentMethod;
import com.example.vebibeer_be.model.repo.PaymentMethodRepo;

@Service
public class PaymentMethodService {
    @Autowired
    PaymentMethodRepo paymentMethodRepo;

    public List<PaymentMethod> getAll() {
        return paymentMethodRepo.findAll();
    }

    public PaymentMethod getById(int paymentMethod_id) {
        return paymentMethodRepo.getReferenceById(paymentMethod_id);
    }

    public void save(PaymentMethod paymentMethod) {
        paymentMethodRepo.save(paymentMethod);
    }

    public void delete(int paymentMethod_id) {
        paymentMethodRepo.deleteById(paymentMethod_id);
    }

}