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

import com.example.vebibeer_be.model.entities.PaymentMethod;
import com.example.vebibeer_be.model.service.PaymentMethodService;

@RestController
@RequestMapping("/paymentMethod")
public class RestPaymentMethodController {
    @Autowired
    PaymentMethodService paymentMethodService = new PaymentMethodService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<PaymentMethod>> showList() {
        List<PaymentMethod> PaymentMethods = paymentMethodService.getAll();
        if (PaymentMethods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PaymentMethod>>(PaymentMethods, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<PaymentMethod> save(@RequestBody PaymentMethod newPaymentMethod) {
        if (newPaymentMethod == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PaymentMethod PaymentMethod = paymentMethodService.getById(newPaymentMethod.getPaymentMethod_id());
        if (PaymentMethod == null) {
            paymentMethodService.save(PaymentMethod);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        paymentMethodService.save(PaymentMethod);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<PaymentMethod> getById(@PathVariable(name = "id")int PaymentMethod_id) {
        PaymentMethod PaymentMethod = paymentMethodService.getById(PaymentMethod_id);
        if (PaymentMethod == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<PaymentMethod>(PaymentMethod, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<PaymentMethod> delete(@PathVariable(name = "id") int PaymentMethod_id){
        PaymentMethod PaymentMethod = paymentMethodService.getById(PaymentMethod_id);
        if (PaymentMethod == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        paymentMethodService.delete(PaymentMethod_id);
        return new ResponseEntity<PaymentMethod>(PaymentMethod, HttpStatus.OK);
    }
}
