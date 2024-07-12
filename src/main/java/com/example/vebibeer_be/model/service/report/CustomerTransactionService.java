package com.example.vebibeer_be.model.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.report.CustomerTransactionDetail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@Service
public class CustomerTransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerTransactionDetail> findCustomerTransactions(int busCompanyId) {
        String sql = "SELECT " +
                "c.customer_id, " +
                "c.username, " +
                "c.customer_fullname, " +
                "c.customer_dob, " +
                "c.customer_description, " +
                "c.customer_gender, " +
                "c.customer_nationality, " +
                "t.transaction_status, " +
                "t.transaction_time_edit, " +
                "t.transaction_vat, " +
                "pm.payment_method_name, " +
                "tk.ticket_price, " +
                "tk.ticket_seat, " +
                "tk.ticket_status, " +
                "r.route_start_time, " +
                "r.route_end_time, " +
                "bc.bus_company_name " +
                "FROM customer c " +
                "JOIN transaction t ON c.customer_id = t.customer_id " +
                "JOIN ticket_transaction tt ON t.transaction_id = tt.transaction_id " +
                "JOIN ticket tk ON tt.ticket_id = tk.ticket_id " +
                "JOIN route r ON tk.route_id = r.route_id " +
                "JOIN buscompany bc ON r.bus_company_id = bc.bus_company_id " +
                "JOIN payment_method pm ON t.payment_method_id = pm.payment_method_id " +
                "WHERE bc.bus_company_id = ? " +
                "ORDER BY c.customer_id, t.transaction_id, tk.ticket_id";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, busCompanyId);
        List<CustomerTransactionDetail> transactions = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Map<String, Object> row : rows) {
            LocalDate dob = row.get("customer_dob") == null ? null
                    : LocalDate.parse(row.get("customer_dob").toString(), dateFormatter);
            LocalDateTime timeEdit = parseDateTime(row.get("transaction_time_edit"), dateTimeFormatter);
            LocalDateTime startTime = parseDateTime(row.get("route_start_time"), dateTimeFormatter);
            LocalDateTime endTime = parseDateTime(row.get("route_end_time"), dateTimeFormatter);

            Double transactionVAT = toDouble(row.get("transaction_vat"));
            Double ticketPrice = toDouble(row.get("ticket_price"));

            CustomerTransactionDetail transaction = new CustomerTransactionDetail(
                    (Integer) row.get("customer_id"),
                    (String) row.get("username"),
                    (String) row.get("customer_fullname"),
                    dob,
                    (String) row.get("customer_description"),
                    (String) row.get("customer_gender"),
                    (String) row.get("customer_nationality"),
                    (String) row.get("transaction_status"),
                    timeEdit,
                    transactionVAT,
                    (String) row.get("payment_method_name"),
                    ticketPrice,
                    (String) row.get("ticket_seat"),
                    (String) row.get("ticket_status"),
                    startTime,
                    endTime,
                    (String) row.get("bus_company_name"));
            transactions.add(transaction);
        }

        return transactions;
    }

    private LocalDateTime parseDateTime(Object dateTimeObj, DateTimeFormatter formatter) {
        if (dateTimeObj == null)
            return null;
        try {
            return LocalDateTime.parse(dateTimeObj.toString(), formatter);
        } catch (Exception e) {
            // Log error or handle it as necessary
            return null;
        }
    }

    private Double toDouble(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        }
        return null; // or handle other types as needed
    }
}