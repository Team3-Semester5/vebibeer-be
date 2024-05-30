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

import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class RestTransactionController {
    @Autowired
    TransactionService transactionService = new TransactionService();

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Transaction>> showList() {
        List<Transaction> Transactions = transactionService.getAll();
        if (Transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Transaction>>(Transactions, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Transaction> save(@RequestBody Transaction newTransaction) {
        if (newTransaction == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Transaction Transaction = transactionService.getById(newTransaction.getTransaction_id());
        if (Transaction == null) {
            transactionService.save(Transaction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        transactionService.save(Transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Transaction> getById(@PathVariable(name = "id")int Transaction_id) {
        Transaction Transaction = transactionService.getById(Transaction_id);
        if (Transaction == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Transaction>(Transaction, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Transaction> delete(@PathVariable(name = "id") int Transaction_id){
        Transaction Transaction = transactionService.getById(Transaction_id);
        if (Transaction == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        transactionService.delete(Transaction_id);
        return new ResponseEntity<Transaction>(Transaction, HttpStatus.OK);
    }
}
