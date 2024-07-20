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


@Service
public class CustomerTransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<CustomerTransactionDetail> findCustomerTransactions(int busCompanyId) {
        String sql = "SELECT " +
                "c.customer_id, c.username, c.customer_fullname, c.customer_dob, " +
                "c.customer_description, c.customer_gender, c.customer_nationality, " +
                "t.transaction_status, t.transaction_time_edit, t.transaction_vat, " +
                "pm.payment_method_name, tk.ticket_price, tk.ticket_seat, tk.ticket_status, " +
                "r.route_start_time, r.route_end_time, bc.bus_company_name " +
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

        for (Map<String, Object> row : rows) {
            CustomerTransactionDetail transaction = new CustomerTransactionDetail(
                (Integer) row.get("customer_id"),
                (String) row.get("username"),
                (String) row.get("customer_fullname"),
                row.get("customer_dob") != null ? row.get("customer_dob").toString() : null,
                (String) row.get("customer_description"),
                (String) row.get("customer_gender"),
                (String) row.get("customer_nationality"),
                (String) row.get("transaction_status"),
                parseLocalDateTime(row.get("transaction_time_edit")),
                toDouble(row.get("transaction_vat")),
                (String) row.get("payment_method_name"),
                toDouble(row.get("ticket_price")),
                (String) row.get("ticket_seat"),
                (String) row.get("ticket_status"),
                parseLocalDateTime(row.get("route_start_time")),
                parseLocalDateTime(row.get("route_end_time")),
                (String) row.get("bus_company_name"));
            transactions.add(transaction);
        }

        return transactions;
    }

    private LocalDateTime parseLocalDateTime(Object dateTimeObj) {
        if (dateTimeObj == null)
            return null;
        try {
            return LocalDateTime.parse(dateTimeObj.toString(), DATE_TIME_FORMATTER);
        } catch (Exception e) {
            // Log error or handle it as necessary
            return null;
        }
    }

    private LocalDate parseLocalDate(Object dateObj) {
        if (dateObj == null)
            return null;
        try {
            return LocalDate.parse(dateObj.toString(), DATE_FORMATTER);
        } catch (Exception e) {
            // Log error or handle it as necessary
            return null;
        }
    }

    private Double toDouble(Object value) {
        if (value == null)
            return null;
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            try {
                return Double.parseDouble(value.toString());
            } catch (NumberFormatException e) {
                // Log or handle parse error
                return null;
            }
        }
    }
}