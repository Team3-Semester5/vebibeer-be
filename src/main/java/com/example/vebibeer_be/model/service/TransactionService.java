package com.example.vebibeer_be.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.repo.TransactionRepo;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    public List<Transaction> getAll(){
        return transactionRepo.findAll();
    }

    public Transaction getById(int transaction_id){
        return transactionRepo.getReferenceById(transaction_id);
    }

    public void save(Transaction transaction){
        transactionRepo.save(transaction);
    }

    public void delete(int transaction_id){
        transactionRepo.deleteById(transaction_id);
    }

    public List<Object[]> getTransactionInfoByCustomerId(int customerId) {
        List<Object[]> transactions = transactionRepo.findTransactionInfoByCustomerId(customerId);
        return transactions;
    }

}
