
package com.example.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.entity.Transaction;
import com.example.model.repo.TransactionRepository;


@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;


    // create the transaction
    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    // retrive the trasaction
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    // retrive the transaction by id
    public Optional<Transaction> getTransactionById(int id){
        return transactionRepository.findById(id);
    }


    // update the transaction
    public Transaction updateTransaction(int id, Transaction transaction){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if(optionalTransaction.isPresent()){
            Transaction existTransaction = optionalTransaction.get();
            existTransaction.setCustomer(transaction.getCustomer());
            existTransaction.setPaymentMethod(transaction.getPaymentMethod());
            existTransaction.setTicket(transaction.getTicket());
            existTransaction.setTransaction_point(transaction.getTransaction_point());
            existTransaction.setTransaction_status(transaction.getTransaction_status());
            existTransaction.setTransaction_time_to_edit(transaction.getTransaction_time_to_edit());
            existTransaction.setTransaction_VAT(transaction.getTransaction_VAT());
            existTransaction.setVoucher(transaction.getVoucher());
            return existTransaction;
        }else {
            throw new RuntimeException( "Transaction was not found with id"+ id);
        }
    }

    // delete the tracsaction by id
    public void deleteTransaction(int id){
        transactionRepository.deleteById(id);
    }
}
