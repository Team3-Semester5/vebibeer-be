package com.example.vebibeer_be.model.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.vebibeer_be.model.entities.Transaction;
import com.example.vebibeer_be.model.entities.BusCompany.Route;
import com.example.vebibeer_be.model.entities.BusCompany.Ticket;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.repo.TransactionRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.TicketRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.example.vebibeer_be.dto.OderDTO;
import com.example.vebibeer_be.dto.TransactionDetailDTO;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TicketRepo ticketRepo;

    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    public Transaction getById(int transaction_id) {
        return transactionRepo.getReferenceById(transaction_id);
    }

    public void save(Transaction transaction) {
        try {
            sendMailBookingSuccess(transaction);
        } catch (Exception e) {
            System.out.println(e);
        }

        transactionRepo.save(transaction);
    }

    public void delete(int transaction_id) {
        transactionRepo.deleteById(transaction_id);
    }

    public List<TransactionDetailDTO> getTransactionInfoByCustomerId(int customerId) {
        List<Transaction> transactions = transactionRepo.findByCustomer(customerRepo.getReferenceById(customerId));
        List<TransactionDetailDTO> transactionDetails = new ArrayList<>();

        for (Transaction transaction : transactions) {
            int transaction_id = transaction.getTransaction_id();
            String startLocation = "";
            String endLocation = "";
            String busCompany = "";
            double totalTicketPrice = 0;
            int totalTickets = 0;
            String paymentMethod = transaction.getPaymentMethod() != null
                    ? transaction.getPaymentMethod().getPaymentMethod_name()
                    : "";
            String transactionStatus = transaction.getTransaction_status();
            String voucherCode = transaction.getVoucher() != null ? transaction.getVoucher().getVoucher_code() : "";
            double saleUp = transaction.getVoucher() != null ? transaction.getVoucher().getSaleUp() : 0;
            int points = transaction.getTransaction_point();
            StringBuilder ticketSeats = new StringBuilder();
            String routeStartTime = "";
            String routeEndTime = "";
            String ticketIds = "";

            for (Ticket ticket : transaction.getTickets()) {
                if (startLocation.isEmpty() && ticket.getRoute().getStartLocation() != null) {
                    startLocation = ticket.getRoute().getStartLocation().getLocation_name();
                }
                if (endLocation.isEmpty() && ticket.getRoute().getEndLocation() != null) {
                    endLocation = ticket.getRoute().getEndLocation().getLocation_name();
                }
                if (busCompany.isEmpty() && ticket.getRoute().getBusCompany() != null) {
                    busCompany = ticket.getRoute().getBusCompany().getBusCompany_fullname();
                }
                totalTicketPrice += ticket.getTicket_price();
                totalTickets++;
                if (ticketSeats.length() > 0) {
                    ticketSeats.append(", ");
                }
                ticketIds += ticket.getTicket_id() + ",";
                ticketSeats.append(ticket.getTicket_seat());
                if (routeStartTime.isEmpty() && ticket.getRoute().getRoute_startTime() != null) {
                    routeStartTime = ticket.getRoute().getRoute_startTime().toString();
                }
                if (routeEndTime.isEmpty() && ticket.getRoute().getRoute_endTime() != null) {
                    routeEndTime = ticket.getRoute().getRoute_endTime().toString();
                }
            }
            totalTicketPrice = transaction.getTransaction_point();
            TransactionDetailDTO detailDTO = new TransactionDetailDTO(
                    startLocation,
                    endLocation,
                    busCompany,
                    totalTicketPrice,
                    totalTickets,
                    paymentMethod,
                    transactionStatus,
                    voucherCode,
                    saleUp,
                    100,
                    ticketSeats.toString(),
                    routeStartTime,
                    routeEndTime);
            detailDTO.setTicketIds(ticketIds);
            detailDTO.setTransaction_id(transaction_id);
            detailDTO.setCustomer_id(customerId);
            transactionDetails.add(detailDTO);
        }

        return transactionDetails;
    }

    private double calculateRefund(LocalDateTime timeNow, LocalDateTime timeStart) {
        int comparisonResult = timeNow.compareTo(timeStart);
        if (comparisonResult <= 48 && comparisonResult > 24) {
            return 0.90;
        } else if (comparisonResult <= 24 && comparisonResult > 2) {
            return 0.65;
        } else if (comparisonResult <= 2 && comparisonResult > 0) {
            return 0.30;
        } else if (comparisonResult < 0) {
            return 0;
        } else {
            return 1.00;
        }
    }

    public Transaction cancelTransaction(TransactionDetailDTO transactionDetailDTO) {
        Transaction transaction = transactionRepo.getReferenceById(transactionDetailDTO.getTransaction_id());
        transaction.setTransaction_status("Pending");
        LocalDateTime timeNow = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        // Convert LocalDateTime to ZonedDateTime
        ZonedDateTime zonedDateTime = timeNow.atZone(zoneId);
        // Convert ZonedDateTime to Instant
        Timestamp nowTimestamp = Timestamp.from(zonedDateTime.toInstant());
        transaction.setTransaction_timeEdit(nowTimestamp);
        return transactionRepo.save(transaction);
    }

    // view information customer
    public List<OderDTO> getTransactionsByBusCompanyId(int busCompanyId) {
        List<Object[]> results = transactionRepo.findTransactionsByBusCompanyId(busCompanyId);
        List<OderDTO> transactions = new ArrayList<>();

        for (Object[] result : results) {
            OderDTO dto = new OderDTO(
                    result[0] != null ? (Integer) result[0] : null, // transactionId
                    result[1] != null ? (String) result[1] : null, // transactionStatus
                    result[2] != null ? ((Timestamp) result[2]).toLocalDateTime() : null, // transactionTimeEdit
                    result[3] != null ? (Integer) result[3] : null, // customerId
                    result[4] != null ? (String) result[4] : null, // username
                    result[5] != null ? (String) result[5] : null, // customerFullName
                    result[6] != null ? (Integer) result[6] : null, // paymentMethodId
                    result[7] != null ? (String) result[7] : null, // paymentMethodName
                    result[8] != null ? (String) result[8] : null, // ticketId
                    result[9] != null ? ((Number) result[9]).doubleValue() : null, // ticketPrice
                    result[10] != null ? (String) result[10] : null, // ticketSeat
                    result[11] != null ? (Integer) result[11] : null, // carId
                    result[12] != null ? (String) result[12] : null // carName
            );
            transactions.add(dto);
        }

        return transactions;
    }

    // cancel
    public Transaction confirmCancelOrder(OderDTO cancelOrder) {
        Transaction transaction = transactionRepo.getReferenceById(cancelOrder.getTransactionId());
        String[] ticketId = cancelOrder.getTicketId().split(",");
        Customer customer = customerRepo.getReferenceById(cancelOrder.getCustomerId());
        for (String string : ticketId) {
            Ticket ticket = ticketRepo.getReferenceById(Integer.parseInt(string));
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timeStart = ticket.getRoute().getRoute_startTime().toLocalDateTime();

            long totalRefund = customer.getPoint()
                    + (long) (ticket.getTicket_price() * calculateRefund(timeNow, timeStart));
            System.out.println(totalRefund);
            customer.setPoint(totalRefund);
            customerRepo.save(customer);
            ticket.setTicket_status("Empty");
            ticketRepo.save(ticket);
            transaction.setTransaction_status("Cancel");
            try {
                sendMailStatusCancelTicket(customer);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return transactionRepo.save(transaction);
    }

    @Autowired
    JavaMailSender mailSender;

    public void sendMailStatusCancelTicket(Customer user)
            throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getUsername();
        String fromAddress = "chumlu2102@gmail.com";
        String senderName = "Vebibeer";
        String subject = "Customer Feedback Request";
        String content = "<!DOCTYPE html>\n" + //
                "<html lang=\"en\">\n" + //
                "<head>\n" + //
                "    <meta charset=\"UTF-8\">\n" + //
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
                "    <title>Customer Feedback Request</title>\n" + //
                "</head>\n" + //
                "<body style=\"margin: 0; padding: 20px; font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9;\">\n"
                + //
                "    <div style=\"max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.10);\">\n"
                + //
                "        <h2 style=\"color: #4A90E2;\">Hello, [[name]]!</h2>\n" + //
                "        <p style=\"font-size: 16px; line-height: 1.5;\">\n" + //
                "            Thanks for using our application. We hope it meets your expectations!\n" + //
                "        </p>\n" + //
                "        <p style=\"font-size: 16px; line-height: 1.5;\">\n" + //
                "            If there's anything that did not meet your expectations or if you have any feedback for us, we would love to hear it. Your feedback is crucial for us to improve.\n"
                + //
                "        </p>\n" + //
                "        <p style=\"text-align: center; margin-top: 20px;\">\n" + //
                "            <a href=\"[[FEEDBACK_URL]]\" target=\"_blank\" style=\"background-color: #4A90E2; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-size: 18px; display: inline-block;\">Give Feedback</a>\n"
                + //
                "        </p>\n" + //
                "        <p style=\"font-size: 16px; line-height: 1.5; color: #777;\">\n" + //
                "            Thank you,<br>\n" + //
                "            Vebibeer\n" + //
                "        </p>\n" + //
                "    </div>\n" + //
                "</body>\n" + //
                "</html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        helper.setText(content, true);
        mailSender.send(message);

    }

    public void sendMailBookingSuccess(Transaction transaction)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = transaction.getCustomer().getUsername();
        Set<Ticket> tickets = transaction.getTickets();
        String fromAddress = "chumlu2102@gmail.com";
        String senderName = "Vebibeer";
        String subject = "[ORDER SUCCESS]";
        StringBuilder contentBuilder = new StringBuilder();

        contentBuilder.append("<html>")
                .append("<body>")
                .append("<div style='font-family: Arial, sans-serif; padding: 20px;'>")
                .append("<h2 style='color: #4CAF50;'>Order Confirmation</h2>")
                .append("<p>Dear <strong>")
                .append(transaction.getCustomer().getUsername())
                .append("</strong>,</p>")
                .append("<p>Thank you for your order. Here are the details of your tickets:</p>")
                .append("<table style='border-collapse: collapse; width: 100%;'>")
                .append("<tr style='background-color: #f2f2f2;'>")
                .append("<th style='padding: 8px; border: 1px solid #ddd;'>Ticket Seat</th>")
                .append("<th style='padding: 8px; border: 1px solid #ddd;'>From</th>")
                .append("<th style='padding: 8px; border: 1px solid #ddd;'>To</th>")
                .append("<th style='padding: 8px; border: 1px solid #ddd;'>Start time</th>")
                .append("<th style='padding: 8px; border: 1px solid #ddd;'>Price</th>")
                .append("</tr>");

        for (Ticket ticket : tickets) {
            contentBuilder.append("<tr>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>")
                    .append(ticket.getTicket_seat())
                    .append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>")
                    .append(ticket.getRoute().getStartLocation().getLocation_name())
                    .append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>")
                    .append(ticket.getRoute().getEndLocation().getLocation_name())
                    .append("</td>")

                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>")
                    .append(ticket.getRoute().getRoute_startTime())
                    .append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid #ddd;'>")
                    .append(ticket.getTicket_price())
                    .append(".000 VND</td>")
                    .append("</tr>");
        }

        contentBuilder.append("</table>")
                .append("<p>Please verify your registration by clicking the link below:</p>")
                .append("<p><a href='[[URL]]' target='_self' style='color: #ffffff; background-color: #4CAF50; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>VERIFY</a></p>")
                .append("<p>Thank you,<br>Your company name.</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(contentBuilder.toString(), true);
        mailSender.send(message);

    }

}