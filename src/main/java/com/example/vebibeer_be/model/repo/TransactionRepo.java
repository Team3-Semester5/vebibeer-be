package com.example.vebibeer_be.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.Customer.Customer;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    // @Query("SELECT sl.location_name, el.location_name, bc.busCompany_fullname, t.transaction_timeEdit, t.transaction_status, " +
    //    "t.transaction_point, v.saleUp, tk.ticket_price,  pm.paymentMethod_name," +
    //    "v.voucher_code AS voucher_code, " +
    //    "GREATEST(0, (tk.ticket_price - COALESCE(t.transaction_point, 0) - COALESCE(v.saleUp, 0))) AS total_amount " +
    //    "FROM Transaction t " +
    //    "JOIN t.tickets tk " +
    //    "JOIN tk.route r " +
    //    "JOIN r.startLocation sl " +
    //    "JOIN r.endLocation el " +
    //    "JOIN r.car c " +
    //    "JOIN c.busCompany bc " +
    //    "LEFT JOIN t.voucher v " +
    //    "JOIN t.paymentMethod pm " + 
    //    "WHERE t.customer.customer_id = :customerId " +
    //    "GROUP BY sl.location_name, el.location_name, t.transaction_point, t.transaction_timeEdit, t.transaction_status, " +
    //    "bc.busCompany_fullname, tk.ticket_price, v.saleUp, pm.paymentMethod_name, t.transaction_id, v.voucher_code")
    // List<Object[]> findTransactionInfoByCustomerId(@Param("customerId") int customerId);

    List<Transaction> findByCustomer(Customer customer);

}
