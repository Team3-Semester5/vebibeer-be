package com.example.vebibeer_be.model.service.report;

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

    public ArrayList<BusCompanyByTime> getAllInforBusCompanyByTime() {
        String sql = "SELECT \n" + //
                "    bc.bus_company_name,\n" + //
                "    EXTRACT(YEAR FROM t.transaction_time_edit) AS year,\n" + //
                "    EXTRACT(MONTH FROM t.transaction_time_edit) AS month,\n" + //
                "    COUNT(tk.ticket_id) AS tickets_sold\n" + //
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
                "    t.transaction_status = 'OrderSuccess'  -- Giả sử chỉ tính những giao dịch hoàn thành\n" + //
                "GROUP BY \n" + //
                "    bc.bus_company_name, year, month\n" + //
                "ORDER BY \n" + //
                "    bc.bus_company_name, year, month;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        ArrayList<BusCompanyByTime> busCompanyByTimeList = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            String bus_company_name = (String) map.get("bus_company_name");
            int year = (int) map.get("year");
            int month = (int) map.get("month");
            long tickets_sold = (long) map.get("tickets_sold");
            busCompanyByTimeList.add(new BusCompanyByTime(bus_company_name, year, month, tickets_sold));
        }
        return busCompanyByTimeList;
    }

}
