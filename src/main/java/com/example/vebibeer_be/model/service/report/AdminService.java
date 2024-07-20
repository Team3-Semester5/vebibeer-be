package com.example.vebibeer_be.model.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.report.BusCompanyByTime;
import com.example.vebibeer_be.model.entities.report.TopCustomer;

@Service
public class AdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArrayList<TopCustomer> getTopCustomer() {
        String sql = "SELECT \n" + //
                "    c.customer_id,\n" + //
                "    c.customer_fullname,\n" + //
                "    COUNT(tt.ticket_id) AS number_of_tickets_purchased\n" + //
                "FROM \n" + //
                "    customer c\n" + //
                "JOIN \n" + //
                "    transaction t ON c.customer_id = t.customer_id\n" + //
                "JOIN \n" + //
                "    ticket_transaction tt ON t.transaction_id = tt.transaction_id\n" + //
                "GROUP BY \n" + //
                "    c.customer_id, c.customer_fullname\n" + //
                "ORDER BY \n" + //
                "    number_of_tickets_purchased DESC\n" + //
                "LIMIT 5;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        ArrayList<TopCustomer> topCustomers = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            int customer_id = (int) map.get("customer_id");
            String customer_fullname = (String) map.get("customer_fullname");
            long number_of_tickets_purchased = (long) map.get("number_of_tickets_purchased");
            topCustomers.add(new TopCustomer(customer_id, customer_fullname, number_of_tickets_purchased));
        }
        return topCustomers;
    }

    public ArrayList<BusCompanyByTime> getAllInforBusCompanyByTime(int year) {
        String sql = "SELECT \n" + //
                        "    bc.bus_company_name,\n" + //
                        "    EXTRACT(YEAR FROM t.transaction_time_edit) AS year,\n" + //
                        "    COUNT(tk.ticket_id) AS tickets_sold,\n" + //
                        "    SUM(tk.ticket_price) AS total_revenue\n" + //
                        "FROM \n" + //
                        "    buscompany bc\n" + //
                        "JOIN \n" + //
                        "    route r ON bc.bus_company_id = r.bus_company_id\n" + //
                        "JOIN \n" + //
                        "    ticket tk ON r.route_id = tk.route_id\n" + //
                        "JOIN \n" + //
                        "    ticket_transaction tt ON tk.ticket_id = tt.ticket_id\n" + //
                        "JOIN \n" + //
                        "    transaction t ON tt.transaction_id = t.transaction_id\n" + //
                        "WHERE \n" + //
                        "    t.transaction_status = 'OrderSuccess' and EXTRACT(YEAR FROM t.transaction_time_edit) = ? \n" + //
                        "GROUP BY \n" + //
                        "    bc.bus_company_name, year\n" + //
                        "ORDER BY \n" + //
                        "    bc.bus_company_name, year;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year);
        ArrayList<BusCompanyByTime> busCompanyByTimeList = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            String bus_company_name = (String) map.get("bus_company_name");
            year = (int) map.get("year");
            BigDecimal total_revenue = (BigDecimal) map.get("total_revenue");
            long tickets_sold = (long) map.get("tickets_sold");
            busCompanyByTimeList.add(new BusCompanyByTime(bus_company_name, year,  total_revenue, tickets_sold));
        }
        return busCompanyByTimeList;
    }

}
