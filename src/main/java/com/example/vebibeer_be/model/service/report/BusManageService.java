package com.example.vebibeer_be.model.service.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.report.TicketSalesInfo;

@Service
public class BusManageService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<TicketSalesInfo> findTicketSalesInfoForMonth(int busCompanyId, int year, int month) {
        String sql = "SELECT bc.bus_company_id, bc.bus_company_name,DATE_FORMAT(tr.transaction_time_edit, '%Y-%m') AS transaction_month,COUNT(t.ticket_id) AS number_of_tickets_sold\n"
                + //
                "FROM buscompany bc  JOIN route r ON bc.bus_company_id = r.bus_company_id\n" + //
                "JOIN ticket t ON r.route_id = t.route_id\n" + //
                "JOIN ticket_transaction tt ON t.ticket_id = tt.ticket_id\n" + //
                "JOIN transaction tr ON tt.transaction_id = tr.transaction_id\n" + //
                "WHERE YEAR(tr.transaction_time_edit) = " + year + " AND MONTH(tr.transaction_time_edit) = " + month
                + "\n" + //
                "GROUP BY bc.bus_company_id, bc.bus_company_name, DATE_FORMAT(tr.transaction_time_edit, '%Y-%m') having  bc.bus_company_id = "
                + busCompanyId + "";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<TicketSalesInfo> ticketSalesInfos = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            int busCompany_id = (int) map.get("bus_company_id");
            String busCompany_name = (String) map.get("bus_company_name");
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-mm");
            // LocalDateTime info_time = LocalDateTime.parse((String) map.get("transaction_month"), formatter);
            String info_time = (String) map.get("transaction_month");
            long amount_tickets = (long) map.get("number_of_tickets_sold");
            TicketSalesInfo ticketSalesInfo = new TicketSalesInfo(busCompany_id, busCompany_name, info_time, amount_tickets);
            ticketSalesInfos.add(ticketSalesInfo);
        }
        return ticketSalesInfos;
    }
}
