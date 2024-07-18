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
    // @Query("SELECT sl.location_name, el.location_name, bc.busCompany_fullname,
    // t.transaction_timeEdit, t.transaction_status, " +
    // "t.transaction_point, v.saleUp, tk.ticket_price, pm.paymentMethod_name," +
    // "v.voucher_code AS voucher_code, " +
    // "GREATEST(0, (tk.ticket_price - COALESCE(t.transaction_point, 0) -
    // COALESCE(v.saleUp, 0))) AS total_amount " +
    // "FROM Transaction t " +
    // "JOIN t.tickets tk " +
    // "JOIN tk.route r " +
    // "JOIN r.startLocation sl " +
    // "JOIN r.endLocation el " +
    // "JOIN r.car c " +
    // "JOIN c.busCompany bc " +
    // "LEFT JOIN t.voucher v " +
    // "JOIN t.paymentMethod pm " +
    // "WHERE t.customer.customer_id = :customerId " +
    // "GROUP BY sl.location_name, el.location_name, t.transaction_point,
    // t.transaction_timeEdit, t.transaction_status, " +
    // "bc.busCompany_fullname, tk.ticket_price, v.saleUp, pm.paymentMethod_name,
    // t.transaction_id, v.voucher_code")
    // List<Object[]> findTransactionInfoByCustomerId(@Param("customerId") int
    // customerId);

    List<Transaction> findByCustomer(Customer customer);

    @Query(value = "SELECT\n" + //
                "    t.transaction_id,\n" + //
                "    t.transaction_status,\n" + //
                "    t.transaction_time_edit,\n" + //
                "    cust.customer_id,\n" + //
                "    cust.username,\n" + //
                "    cust.customer_fullname,\n" + //
                "    t.payment_method_id,\n" + //
                "    pm.payment_method_name,\n" + //
                "    GROUP_CONCAT(tk.ticket_id ORDER BY tk.ticket_id) AS tickets,\n" + //
                "    SUM(tk.ticket_price) AS prices,\n" + //
                "    GROUP_CONCAT(tk.ticket_seat ORDER BY tk.ticket_id) AS seats,\n" + //
                "    ca.car_id,\n" + //
                "    ca.car_code,\n" + //
                "    b.bus_company_id\n" + //
                "FROM\n" + //
                "    transaction t\n" + //
                "JOIN\n" + //
                "    customer cust ON t.customer_id = cust.customer_id\n" + //
                "JOIN \n" + //
                "    payment_method pm ON t.payment_method_id = pm.payment_method_id\n" + //
                "JOIN\n" + //
                "    ticket_transaction tt ON t.transaction_id = tt.transaction_id\n" + //
                "JOIN\n" + //
                "    ticket tk ON tt.ticket_id = tk.ticket_id\n" + //
                "JOIN\n" + //
                "    route r ON tk.route_id = r.route_id\n" + //
                "JOIN \n" + //
                "    car ca ON r.car_id = ca.car_id\n" + //
                "JOIN\n" + //
                "    buscompany b ON r.bus_company_id = b.bus_company_id\n" + //
                "WHERE\n" + //
                "    b.bus_company_id = :busCompanyId\n" + //
                "GROUP BY\n" + //
                "    t.transaction_id, t.transaction_status, t.transaction_time_edit, cust.customer_id, cust.username, cust.customer_fullname,\n" + //
                "    t.payment_method_id, pm.payment_method_name, ca.car_id, ca.car_code, b.bus_company_id\n" + //
                "ORDER BY\n" + //
                "    t.transaction_id;", nativeQuery = true)
    List<Object[]> findTransactionsByBusCompanyId(@Param("busCompanyId") int busCompanyId);

}
