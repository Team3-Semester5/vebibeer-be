package com.example.vebibeer_be.model.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.report.RevenueOfYear;
import java.math.BigDecimal;

@Service
public class RevenueReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Doanh thu của năm cho công ty đó
    public List<RevenueOfYear> findAnnualRevenue(int busCompanyId, int year) {
        String sql = "SELECT bc.bus_company_id, bc.bus_company_name, YEAR(tr.transaction_time_edit) AS transaction_year, SUM(t.ticket_price) AS total_earnings "
                +
                "FROM buscompany bc " +
                "JOIN route r ON bc.bus_company_id = r.bus_company_id " +
                "JOIN ticket t ON r.route_id = t.route_id " +
                "JOIN ticket_transaction tt ON t.ticket_id = tt.ticket_id " +
                "JOIN transaction tr ON tt.transaction_id = tr.transaction_id " +
                "WHERE YEAR(tr.transaction_time_edit) = ? " +
                "GROUP BY bc.bus_company_id, bc.bus_company_name, YEAR(tr.transaction_time_edit) " +
                "HAVING bc.bus_company_id = ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year, busCompanyId);
        List<RevenueOfYear> revenueOfYears = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            int busCompany_id = (int) row.get("bus_company_id");
            String busCompany_name = (String) row.get("bus_company_name");
            String transaction_year = String.valueOf(row.get("transaction_year"));
            BigDecimal totalEarningsBD = (BigDecimal) row.get("total_earnings");
            double total_earnings = totalEarningsBD.doubleValue(); // Correct conversion from BigDecimal to double
            RevenueOfYear revenueOfYear = new RevenueOfYear(busCompany_id, busCompany_name, transaction_year,
                    total_earnings);
            revenueOfYears.add(revenueOfYear);
        }

        return revenueOfYears;
    }

    // top 5 busCompany
    public List<RevenueOfYear> findTopCompaniesByRevenue(int year) {
        String sql = "SELECT bc.bus_company_id, bc.bus_company_name, " +
                "YEAR(tr.transaction_time_edit) AS transaction_year, " +
                "SUM(t.ticket_price) AS total_earnings " +
                "FROM buscompany bc " +
                "JOIN route r ON bc.bus_company_id = r.bus_company_id " +
                "JOIN ticket t ON r.route_id = t.route_id " +
                "JOIN ticket_transaction tt ON t.ticket_id = tt.ticket_id " +
                "JOIN transaction tr ON tt.transaction_id = tr.transaction_id " +
                "WHERE YEAR(tr.transaction_time_edit) = ? " +
                "GROUP BY bc.bus_company_id, bc.bus_company_name, YEAR(tr.transaction_time_edit) " +
                "ORDER BY total_earnings DESC " +
                "LIMIT 5";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year);
        List<RevenueOfYear> topRevenueCompanies = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            RevenueOfYear revenueOfYear = new RevenueOfYear(
                    (Integer) row.get("bus_company_id"),
                    (String) row.get("bus_company_name"),
                    String.valueOf(row.get("transaction_year")),
                    ((BigDecimal) row.get("total_earnings")).doubleValue());
            topRevenueCompanies.add(revenueOfYear);
        }

        return topRevenueCompanies;
    }

    // Tổng Doanh thu tháng của công ty đó
    public List<RevenueOfYear> findMonthlyRevenueByCompanyAndMonth(int busCompanyId, int year, int month) {
        String sql = "SELECT bc.bus_company_id, bc.bus_company_name, " +
                "YEAR(tr.transaction_time_edit) AS transaction_year, " +
                "MONTH(tr.transaction_time_edit) AS transaction_month, " +
                "SUM(t.ticket_price) AS total_earnings " +
                "FROM buscompany bc " +
                "JOIN route r ON bc.bus_company_id = r.bus_company_id " +
                "JOIN ticket t ON r.route_id = t.route_id " +
                "JOIN ticket_transaction tt ON t.ticket_id = tt.ticket_id " +
                "JOIN transaction tr ON tt.transaction_id = tr.transaction_id " +
                "WHERE YEAR(tr.transaction_time_edit) = ? AND " +
                "MONTH(tr.transaction_time_edit) = ? AND " +
                "bc.bus_company_id = ? " +
                "GROUP BY bc.bus_company_id, bc.bus_company_name, YEAR(tr.transaction_time_edit), MONTH(tr.transaction_time_edit)";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year, month, busCompanyId);
        List<RevenueOfYear> monthlyRevenue = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            RevenueOfYear revenue = new RevenueOfYear(
                    (Integer) row.get("bus_company_id"),
                    (String) row.get("bus_company_name"),
                    row.get("transaction_year") + "-" + row.get("transaction_month"), // Formats year-month
                    ((BigDecimal) row.get("total_earnings")).doubleValue() // Convert BigDecimal to double
            );
            monthlyRevenue.add(revenue);
        }

        return monthlyRevenue;
    }
}