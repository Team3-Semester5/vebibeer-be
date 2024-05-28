package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.PaymentMethod;
import com.example.model.repo.PaymentMethodRepository;

@Service
public class PaymentMethodService {
    
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // Create a new payment method
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    // Retrieve all payment methods
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    // Retrieve a payment method by its ID
    public Optional<PaymentMethod> getPaymentMethodById(int id) {
        return paymentMethodRepository.findById(id);
    }

    // Update a payment method
    public PaymentMethod updatePaymentMethod(int id, PaymentMethod paymentMethodDetails) {
        Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findById(id);
        if (optionalPaymentMethod.isPresent()) {
            PaymentMethod existingPaymentMethod = optionalPaymentMethod.get();
            existingPaymentMethod.setPaymentmethod_name(paymentMethodDetails.getPaymentmethod_name());
            return paymentMethodRepository.save(existingPaymentMethod);
        } else {
            throw new RuntimeException("PaymentMethod not found with id " + id);
        }
    }

    // Delete a payment method by its ID
    public void deletePaymentMethod(int id) {
        paymentMethodRepository.deleteById(id);
    }
}
