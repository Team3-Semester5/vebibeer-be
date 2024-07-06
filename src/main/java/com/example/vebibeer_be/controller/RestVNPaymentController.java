package com.example.vebibeer_be.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.vebibeer_be.dto.OrderRequest;
import com.example.vebibeer_be.model.entities.PaymentMethod;
import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.PaymentMethodService;
import com.example.vebibeer_be.model.service.TransactionService;
import com.example.vebibeer_be.model.service.BusCompanyService.TicketService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;
import com.example.vebibeer_be.vnpay.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cuong")
public class RestVNPaymentController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    TransactionService transactionService = new TransactionService();

    @Autowired
    PaymentMethodService paymentMethodService = new PaymentMethodService();

    @Autowired
    CustomerService customerService = new CustomerService();

    @Autowired
    TicketService ticketService = new TicketService();

    @PostMapping("/submitOrder")
    public ResponseEntity<Map<String, String>> submitOrder(@RequestBody OrderRequest orderRequest,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "");
        String vnpayUrl = vnPayService.createOrder(orderRequest.getAmount(), orderRequest.getOrderInfo(), baseUrl);

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", vnpayUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-payment")
    public RedirectView GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String status = "OrderFail";
        System.out.println(orderInfo);
        if (paymentStatus == 1) {
            status = "OrderSuccess";
            String[] arr = orderInfo.split(";");
            Customer customer = customerService.findByUsername(arr[0]).orElse(null);
            Set<Ticket> tickets = new HashSet<>();
            String[] ticketIds = arr[1].split(",");
            for (String ticketId : ticketIds) {
                if (!ticketId.equals("")) {
                    Ticket ticket = ticketService.getById(Integer.parseInt(ticketId));
                    ticket.setTicket_status("Not empty");
                    ticketService.save(ticket);
                    tickets.add(ticket);
                }

            }
            double VAT = 0.1;
            int point = Integer.parseInt(totalPrice) / 1000;
            PaymentMethod paymentMethod = paymentMethodService.getById(4);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            java.sql.Date transaction_timeEdit = new Date(100000000);
            try {
                java.sql.Date date = (Date) sdf.parse(paymentTime);
                transaction_timeEdit = new java.sql.Date(date.getTime());
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
            Transaction transaction = new Transaction(paymentStatus, VAT, point, status, transaction_timeEdit, tickets,
                    customer, null, paymentMethod);
            transactionService.save(transaction);
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:3000/payment-result?status=" + status);
        return redirectView;
    }
}
