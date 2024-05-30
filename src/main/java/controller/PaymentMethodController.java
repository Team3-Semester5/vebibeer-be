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

import com.example.model.entity.PaymentMethod;
import com.example.model.service.PaymentMethodService;

@RestController
@RequestMapping("/api/paymentmethods")
public class PaymentMethodController {
    
    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    public PaymentMethod createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.createPaymentMethod(paymentMethod);
    }

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable int id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodService.getPaymentMethodById(id);
        if (paymentMethod.isPresent()) {
            return ResponseEntity.ok(paymentMethod.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable int id, @RequestBody PaymentMethod paymentMethodDetails) {
        try {
            PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodDetails);
            return ResponseEntity.ok(updatedPaymentMethod);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable int id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}

