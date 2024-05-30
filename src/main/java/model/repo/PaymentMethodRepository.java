package com.example.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.entity.PaymentMethod;

public interface PaymentMethodRepository  extends JpaRepository <PaymentMethod , Integer>{

    
}
